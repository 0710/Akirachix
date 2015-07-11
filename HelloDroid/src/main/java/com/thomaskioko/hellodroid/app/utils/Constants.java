/*
 * Copyright (c) 2014. Thomas Kioko.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.thomaskioko.hellodroid.app.utils;

/**
 * This class contains static variables used multiple times across the application.
 *
 * @author <a href="kiokotomas@gmail.com">Thomas Kioko</a>
 * @version Version 1.0
 */
public class Constants {

    /**
     * Testing in localhost using wamp or xampp.
     * use http://10.0.2.2/ to connect to your localhost ie http://localhost/
     */

    public static final String LOCALHOST_URL = "http://" + "192.168.45.37/" + "/Akirachix/PHP/API/v1/";

    public static final String LOGIN_TAG = "login";
    public static final String REGISTER_TAG = "register";

    // JSON Response node names
    public static String KEY_JSON_SUCCESS = "success";
    public static String KEY_JSON_ERROR = "error";
    public static String KEY_JSON_ERROR_MSG = "error_message";
    public static String KEY_JSON_UID = "uid";
    public static String KEY_JSON_FIRST_NAME = "firstName";
    public static String KEY_JSON_LAST_NAME = "lastName";
    public static String KEY_JSON_USER_NAME = "userName";
    public static String KEY_JSON_EMAIL = "email";
    public static String KEY_JSON_CREATED_AT = "created_at";
}
