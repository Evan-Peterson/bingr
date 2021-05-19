package com.bingr.app.net_utils;

/**
 * A class to contain static constants of target server URLs
 *
 * @author vb_5
 */
public class Const {
    /** Websocket base URL **/
    public static final String URL_WS = "ws://coms-309-038.cs.iastate.edu:8080/websocket/";

    /** The URL for user login */
    public static final String URL_LOGIN = "http://coms-309-038.cs.iastate.edu:8080/users/login";

    /** The URL for user registration */
    public static final String URL_REGISTER = "http://coms-309-038.cs.iastate.edu:8080/users";

    /** The URL for editing a profile */
    public static final String URL_EDIT_PROFILE = "http://coms-309-038.cs.iastate.edu:8080/users/bio/";

    /** The URL for adding a liked movie **/
    public static final String URL_LIKE_MOVIE = "http://coms-309-038.cs.iastate.edu:8080/users/like/";

    /** The URL for getting all friends **/
    public static final String URL_GET_FRIENDS = "http://coms-309-038.cs.iastate.edu:8080/users/friends/";

}