import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ZendeskHelper {
  static final MethodChannel _channel =
      MethodChannel('alhasan/zendesk_channel');

  /// Initialize the Zendesk SDK with the provided [accountKey] and [appId]
  ///
  static Future<void> initialize(String accountKey, String appId) async {
    await _channel.invokeMethod<void>('initialize', {
      'accountKey': accountKey,
      'appId': appId,
    });
  }

  /// Convenience utility to prefill visitor information and optionally set
  /// a support [department]
  static Future<void> setVisitorInfo({
    String? name,
    String? email,
    String? phoneNumber,
    String? department,
  }) async {
    await _channel.invokeMethod<void>('setVisitorInfo', {
      'name': name,
      'email': email,
      'phoneNumber': phoneNumber,
      'department': department,
    });
  }

  static Future<void> startChat({
    Color? primaryColor,
    bool isPreChatFormEnabled = true,
    bool isAgentAvailabilityEnabled = true,
    bool isChatTranscriptPromptEnabled = true,
    bool isOfflineFormEnabled = true,
  }) async {
    await _channel.invokeMethod<void>('startChat', {
      'primaryColor': primaryColor?.value,
      'isPreChatFormEnabled': isPreChatFormEnabled,
      'isAgentAvailabilityEnabled': isAgentAvailabilityEnabled,
      'isChatTranscriptPromptEnabled': isChatTranscriptPromptEnabled,
      'isOfflineFormEnabled': isOfflineFormEnabled
    });
  }
}
