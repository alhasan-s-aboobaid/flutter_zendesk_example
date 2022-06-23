package com.alhasan.zendesk.zendesk;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zendesk.logger.Logger;

import java.util.Objects;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import zendesk.chat.BuildConfig;
import zendesk.chat.Chat;
import zendesk.chat.ChatConfiguration;
import zendesk.chat.ChatEngine;
import zendesk.chat.ChatMenuAction;
import zendesk.chat.ChatProvider;
import zendesk.chat.ProfileProvider;
import zendesk.chat.VisitorInfo;
import zendesk.messaging.MessagingActivity;

public class MainActivity extends FlutterActivity {

    private static final String PLATFORM_CHANNEL = "alhasan/zendesk_channel";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), PLATFORM_CHANNEL).setMethodCallHandler(
                (call, result) -> {
                    System.out.println(call.method);
                    if(call.method.equals("initialize")) {
                        initialize(call);
                        result.success(true);
                    }
                    if(call.method.equals("setVisitorInfo")) {
                        setVisitorInfo(call);
                        result.success(true);
                    }
                    if(call.method.equals("startChat")) {
                        startChat(call);
                        result.success(true);
                    }
                }
        );

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void startChat(MethodCall call) {
        boolean isPreChatFormEnabled = Boolean.TRUE.equals(call.argument("isPreChatFormEnabled"));
        boolean isAgentAvailabilityEnabled = Boolean.TRUE.equals(call.argument("isAgentAvailabilityEnabled"));
        boolean isChatTranscriptPromptEnabled = Boolean.TRUE.equals(call.argument("isChatTranscriptPromptEnabled"));
        boolean isOfflineFormEnabled = Boolean.TRUE.equals(call.argument("isOfflineFormEnabled"));
        ChatConfiguration chatConfiguration = ChatConfiguration.builder()

                .withAgentAvailabilityEnabled(isAgentAvailabilityEnabled)
                .withTranscriptEnabled(isChatTranscriptPromptEnabled)
                .withOfflineFormEnabled(isOfflineFormEnabled)
                .withPreChatFormEnabled(isPreChatFormEnabled)
                .withChatMenuActions(ChatMenuAction.END_CHAT)
                .build();

        MessagingActivity.builder()
                .withToolbarTitle("Contact Us")
                .withEngines(ChatEngine.engine())
                .show(getActivity(), chatConfiguration);
    }

    private void setVisitorInfo(MethodCall call) {

        String name = call.argument("name");
        String email = call.argument("email");
        String phoneNumber = call.argument("phoneNumber");
        String department = call.argument("department");

        ProfileProvider profileProvider = Objects.requireNonNull(Chat.INSTANCE.providers()).profileProvider();
        ChatProvider chatProvider = Objects.requireNonNull(Chat.INSTANCE.providers()).chatProvider();

        VisitorInfo visitorInfo = VisitorInfo.builder()
                .withName(name)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .build();

        profileProvider.setVisitorInfo(visitorInfo, null);
        chatProvider.setDepartment(department != null ? department : "", null);
    }

    private void initialize(MethodCall call) {
        Logger.setLoggable(BuildConfig.DEBUG);
        String accountKey = call.argument("accountKey");
        String applicationId = call.argument("applicationId");
        Chat.INSTANCE.init(getActivity(), accountKey != null ? accountKey : "", applicationId != null ? applicationId : "");
    }
}
