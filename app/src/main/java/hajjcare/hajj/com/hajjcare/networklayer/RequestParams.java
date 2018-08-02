package hajjcare.hajj.com.hajjcare.networklayer;

/**
 * Created by amra on 2/2/2018.
 */

public class RequestParams {

    public static String VALUE_SERVICE_LOGIN = "user/isuser";
    public static String VALUE_SERVICE_HELP_ME = "request/helpMe";
    public static String VALUE_SERVICE_SIGN_UP = "register";
    public static String VALUE_SERVICE_PROFILE ="profile";
    public static String VALUE_SERVICE_POSTS ="posts";
    public static String VALUE_TYPE_POST ="post";
    public static String VALUE_TYPE_SLIDES ="slide";
    public static String VALUE_SERVICES ="service";
    public static String VALUE_INSURANCE ="insurance";
    public static String VALUE_BRANCHES ="branches";
    public static String VALUE_BRANCH ="branch";
    public static String VALUE_OFFERS ="offers";
    public static String VALUE_BOOKING ="appointment";
    public static String VALUE_EVENTS="event";
    public static String VALUE_DENTIST="dentist";
    public static String VALUE_BEFORE_AND_AFTER="before_after";
    public static String VALUE_ADD_REPLAY="addReply";
    public static String VALUE_MESSAGES ="messages";
    public static String VALUE_REPLIES ="replies";
    public static String VALUE_UPDATE_PROFILE ="profile/update";
    public static String VALUE_NOTIFY ="profile/updateNotify";
    public static String VALUE_ASK_DOCTOR="askDoctor";








    private String service;
    private String operationType;
    private String operationId;
    private String language;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
