package com.payment.ayushdigitils.utils

/**
 * Created by Rinav Gangar <rinav.dev> on 3/4/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
object C {

    object AppError {
        const val NO_NETWORK_CONNECTION = 3
    }

    const val ACTION_RAISED_HAND_REQ: String = "~A2AGR~RS_HND_REQ_SND"
    const val ACTION_RAISED_HAND_REQ_CANCEL: String = "~A2AGR~RS_HND_CANCEL_SELF"
    const val ACTION_MUTE_SELF: String = "~A2AGR~MUTE_SELF"

    const val ACTION_CHAT_MSG: String = "~A2AGR~CHAT_MSG"

    const val ACTION_RAISED_HAND_ACTIVATE: String = "~A2AGR~RS_HND_ACTIVATE"
    const val ACTION_RAISED_HAND_DEACTIVATE: String = "~A2AGR~RS_HND_DEACTIVATE"
    const val ACTION_LEAVE_CHANNEL: String = "~A2AGR~CHNL_LEAVE"
    const val ACTION_HOST_ERROR: String = "~A2AGR~ERR_HOST"

    const val ACTION_CHANNEL_SETTING: String = "~A2AGR~CHNL_SETTING"

    const val ACTION_RAISED_HAND_ALLOW: String = "~A2AGR~RS_HND_ACCEPT"
    const val ACTION_RAISED_HAND_REJECT: String = "~A2AGR~RS_HND_REJECT"
    const val ACTION_MUTE_USER: String = "~A2AGR~MUTE_USER"

    const val ACTION_USER_COUNT: String = "~A2AGR~USER_COUNT"

    const val ACTION_EMOJI_SMILE: String = "~A2AGR~EMJ_SMILE"
    const val ACTION_EMOJI_CLAP: String = "~A2AGR~EMJ_CLAP"
    const val ACTION_EMOJI_FRAME: String = "~A2AGR~EMJ_FRAME"
    const val ACTION_EMOJI_HEART: String = "~A2AGR~EMJ_HEART"
    const val ACTION_EMOJI_CAP: String = "~A2AGR~EMJ_CAP"
    const val ACTION_EMOJI_THUMB_UP: String = "~A2AGR~EMJ_THUMB_UP"
    const val ACTION_EMOJI_PARTY: String = "~A2AGR~EMJ_PARTY"
    const val ACTION_EMOJI_GLASS: String = "~A2AGR~EMJ_GLASS"
    const val ACTION_EMOJI_SAD: String = "~A2AGR~EMJ_SAD"

    const val EVENT_ACTION_EMOJI: String = "emoji_action"
    const val ACTION_MSG: String = "action_msg"

    const val EVENT_APP_LAUNCH: String = "app_launch"
    const val EVENT_LOGO_CLICK: String = "logo_click"

    const val EVENT_LOGIN_INIT: String = "login_init"
    const val LOGIN_TYPE: String = "status"

    const val EVENT_LOGIN_STATUS: String = "login_status"
    const val USER_TYPE: String = "status"
    const val CREATE_ERR_CODE: String = "firebase_error_code"

    const val EVENT_LOGIN_CANCEL: String = "login_cancel"

    const val EVENT_SCREEN_VIEW: String = "screen_view"

    const val EVENT_ANON_CREATE: String = "anon_create"
    const val ANON_CREATE_STATUS: String = "status"
    const val ANON_CREATE_ERR_CODE: String = "firebase_error_code"

    const val EVENT_LANGUAGE_PREF = "lang_preference"
    const val LANGUAGE_PREF = "lang_pref"
    const val AAWAZ_ID = "a_id"

    const val EVENT_UPDATE_PROFILE = "update_profile"
    const val UPDATE_PROFILE_ACTION = "action"
    const val EDIT_CLICK = "edit_click"
    const val UPDATE_CLICK = "save_click"

    const val EVENT_TOP_BANNER = "top_banner_click"
    const val BANNER_POSITION: String = "banner_position"
    const val BANNER_TYPE: String = "banner_type"
    const val BANNER_ID: String = "banner_id"

    const val EVENT_DOWNLOAD_ALL: String = "download_all"
    const val EVENT_DOWNLOAD_START: String = "download_start"
    const val EVENT_DELETE_DOWNLOAD: String = "delete_download"
    const val EVENT_DOWNLOAD_STATUS: String = "download_status"
    const val DOWNLOAD_STATUS: String = "status"
    const val DOWNLOAD_SUCCESS: Int = 1
    const val DOWNLOAD_FAILED: Int = 0

    const val EVENT_COMMENT: String = "comment"
    const val EVENT_LIVE_COMMENT: String = "live_comment"

    const val EVENT_SUBSCRIBE_SHOW: String = "subscribe_show"
    const val EVENT_UNSUBSCRIBE_SHOW: String = "unsubscribe_show"
    const val EVENT_SUBSCRIBE_CHANNEL: String = "subscribe_channel"
    const val EVENT_UNSUBSCRIBE_CHANNEL: String = "unsubscribe_channel"

    const val EVENT_SHARE_SHOW: String = "share_show"
    const val EVENT_SHARE_LIVE_SHOW: String = "share_live_show"
    const val EVENT_SHARE_EPISODE: String = "share_episode"
    const val EVENT_SHARE_CHANNEL: String = "share_channel"

    const val EVENT_LIKE: String = "like"
    const val EVENT_UNLIKE: String = "unlike"
    const val LIKE_STATUS: String = "status"
    const val LIKE: Int = 1
    const val UNLIKE: Int = 0

    const val EVENT_SEARCH: String = "search"
    const val SEARCH_QUERY: String = "search_query"
    const val SEARCH_RESULT: String = "search_result"
    const val SEARCH_TYPE: String = "type"

    const val EVENT_PLAY_All: String = "play_all"
    const val EVENT_PLAY: String = "play"
    const val EVENT_PAUSE: String = "pause"
    const val EVENT_STOP: String = "stop"
    const val EVENT_RESUME: String = "resume"
    const val EVENT_FAST_FORWARD: String = "fast_forward"
    const val EVENT_REWIND: String = "rewind"
    const val EVENT_SEEK_START: String = "seek_start"
    const val EVENT_SEEK_END: String = "seek_end"
    const val SEEK_START: String = "start_time"
    const val SEEK_END: String = "end_time"

    const val EVENT_ADS_PLAY: String = "ad_play"

    const val EVENT_SPEED: String = "speed"
    const val SPEED: String = "playing_speed"
    const val SPEED_0_5X = 0.5F
    const val SPEED_0_9X = 0.9F
    const val SPEED_1X = 1.0F
    const val SPEED_1_2X = 1.2F
    const val SPEED_1_5X = 1.5F
    const val SPEED_1_75X = 1.75F
    const val SPEED_2X = 2.0F

    const val SPEED_0_5X_TEXT = "0.5x"
    const val SPEED_0_9X_TEXT = "0.9x"
    const val SPEED_1X_TEXT = "1x"
    const val SPEED_1_2X_TEXT = "1.2x"
    const val SPEED_1_5X_TEXT = "1.5x"
    const val SPEED_1_75X_TEXT = "1.75x"
    const val SPEED_2X_TEXT = "2x"



    const val EVENT_SWIPE_UP: String = "swipe_up"
    const val EVENT_SWIPE_LEFT: String = "swipe_left"
    const val EVENT_SWIPE_RIGHT: String = "swipe_right"

    const val EVENT_COMPLETION_RATE_SEC: String = "completion_rate_seconds"
    const val PROGRESS_IN_SEC: String = "progress_seconds"
    const val PROGRESS_TEN_SEC: Int = 10
    const val PROGRESS_SIXTY_SEC: Int = 60

    const val EVENT_COMPLETION_RATE_PER: String = "completion_rate"
    const val PROGRESS_IN_PER: String = "progress_percent"
    const val PROGRESS_TEN_PER: Int = 10
    const val PROGRESS_TWENTY_FIVE_PER: Int = 25
    const val PROGRESS_FIFTY_PER: Int = 50
    const val PROGRESS_SEVENTY_FIVE_PER: Int = 75
    const val PROGRESS_HUNDRED_PER: Int = 98

    const val PLAYER_TIME: String = "player_time"
    const val REPEAT_STATUS: String = "repeat_status"
    const val IS_MUTED: String = "is_muted"
    const val DEVICE_VOLUME: String = "device_volume"

    const val SECTION_TYPE: String = "section_type"
    const val SECTION_NUMBER: String = "section_number"
    const val SECTION_TITLE: String = "section_title"

    const val EVENT_VIEW_CONTENT: String = "view_content"
    const val EVENT_SHOW_DETAILS: String = "show_details"
    const val SECTION_TYPE_BANNER: String = "banner"
    const val SECTION_TYPE_SHOW: String = "show"
    const val SECTION_TYPE_RELATED_SHOW: String = "related_show"
    const val SECTION_TYPE_CHAN: String = "channel"
    const val SECTION_TYPE_SINGLE_CHAN: String = "single_channel"
    const val SECTION_TYPE_CAT: String = "category"
    const val SECTION_TYPE_EPISODE: String = "episode"
    const val SECTION_TYPE_LIVE_EPISODE: String = "live_episode"
    const val SECTION_TYPE_LIVE_SHOW: String = "live_show"
    const val SECTION_TYPE_VIEW_ALL_ALBUM: String = "view_all_album"
    const val SECTION_TYPE_VIEW_ALL_EPISODE: String = "view_all_episode"
    const val SECTION_TYPE_CHAN_PAGE: String = "channel_page"
    const val SECTION_TYPE_CAT_PAGE: String = "category_page"
    const val SECTION_TYPE_PLAYER_PAGE: String = "player_page"

    const val EVENT_RAISED_HAND: String = "raised_hand"
    const val RAISED_HAND_STATUS: String = "raised_hand_status"

    const val EVENT_BROADCAST_TIME: String = "broadcast_time"
    const val BROADCAST_START_END: String = "start_end"

//    const val EVENT_CATEGORY_DETAILS: String = "category_details"
//    const val SECTION_TYPE_BANNER: String = "banner"
//    const val SECTION_TYPE_SHOW: String = "show"
//    const val SECTION_TYPE_CHAN_SHOW: String = "channel_show"
//    const val SECTION_TYPE_CAT_SHOW: String = "category_show"
//    const val SECTION_TYPE_CHAN_PAGE: String = "channel_page"
//    const val SECTION_TYPE_CAT_PAGE: String = "category_page"



    const val IS_ANONYMOUS: String = "is_anonymous"


    const val EVENT_TIME: String = "event_time"
    const val EVENT_NAME: String = "event_name"
    const val EVENT_TITLE: String = "event_title"
    const val PLATFORM: String = "platform"
    const val PAGE_SOURCE: String = "page_source"

    const val SHOW_LANGUAGE: String = "lang"
    const val IS_LIVE: String = "is_live"
    const val SLUG: String = "slug"
    const val CONTENT_TITLE: String = "content_title"
    const val SHOW_ID: String = "show_id"
    const val SHOW_NAME: String = "show_name"
    const val EPISODE_ID: String = "episode_id"
    const val EPISODE_NAME: String = "episode_name"
    const val CHANNEL_ID: String = "channel_id"
    const val CHANNEL_NAME: String = "channel_name"
    const val CATEGORY_ID: String = "category_id"
    const val CATEGORY_NAME: String = "category_name"
    const val TRACK_TYPE: String = "track_type"

    const val EPISODE_NUMBER: String = "episode_number"
    const val MUSIC_PLAYER_TIME: String = "music_player_time"
    const val ADs_TYPE: String = "ad_type"
    const val PLATFORM_NAME: String = "platform_name"
    const val STREAM_PERCENTAGE: String = "stream_percentage"
    const val USER_ID: String = "user_id"


    const val PLAYERHEAD_POSITION: String = "playerhead_position"







    const val SHARE_PLATFORM: String = "on_platform"
    const val CONTENT_LENGTH: String = "content_length"


    const val CONTENT_LANGUAGE: String = "content_language"

    const val SEARCH_TYPE_VOICE: Int = 1
    const val SEARCH_TYPE_TEXT: Int = 0

    const val SUBSCRIBE_STATUS: String = "status"
    const val SUBSCRIBE: Int = 1
    const val UNSUBSCRIBE: Int = 0

    const val PREFS_FILE_NAME = "aawaz_pref"




}
