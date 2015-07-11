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

import com.thomaskioko.hellodroid.app.network.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="kiokotomas@gmail.com">Thomas Kioko</a>
 * @version Version 1.0
 */


public class UserFunctions {

    private JSONParser jsonParser;


    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    /**
     * function make Login Request
     *
     * @param email    Email Address
     * @param password Password
     */
    public JSONObject loginUser(String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.LOGIN_TAG));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        /**
         * Returns a JSON Object
         */
        return jsonParser.getJSONFromUrl(Constants.LOCALHOST_URL, params);
    }

    /**
     * function make Register Request
     *
     * @param firstName Users First Name
     * @param userName  Users user Name
     * @param email     Email Address
     * @param password  Password
     */
    public JSONObject registerUser(String firstName, String lastName, String userName,
                                   String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.REGISTER_TAG));
        params.add(new BasicNameValuePair("firstName", firstName));
        params.add(new BasicNameValuePair("lastName", lastName));
        params.add(new BasicNameValuePair("userName", userName));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        // return json
        return jsonParser.getJSONFromUrl(Constants.LOCALHOST_URL, params);
    }


}
