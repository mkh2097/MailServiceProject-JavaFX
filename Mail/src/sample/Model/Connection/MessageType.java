package sample.Model.Connection;

/***
 * Enumeration for MessageHandler Class
 */
public enum MessageType
{
    /**
     * connect to a user
     */
    Connect,
    /**
     * disconnect user from server
     */
    Disconnect,
    /**
     * send a text message
     */
    SignIn,Register,Receive,Send,Reply,Forward,
    Read,Unread,Important,Unimportant,
    Block,Unblock,
    RemoveMSG,RemoveCONV,SignOut,
    RequestFile
}