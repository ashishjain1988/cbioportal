package org.mskcc.cbio.portal.util;

/**
 * Encapsulates a single XDebug Parameter name/value.
 * The following parameter types are supported: <UL>
 * <LI>COOKIE_TYPE:  HTTP Cookies <LI>USER_TYPE:  Any user supplied parameters
 * <LI>INTERNAL_TYPE:  Any internal parameters, such as timers.
 * <LI>ENVIRONMENT_TYPE:  Any Environment Variables
 * <LI>HTTP_TYPE:  Any HTTP parameters, such as protocol type
 * <LI>HTTP_HEADER_TYPE:  Any HTTP Headers </UL>
 */
public class XDebugParameter {
    /**
     * HTTP Cookie Type.
     */
    public static final int COOKIE_TYPE = 0;

    /**
     * User Type; for example: form parameters.
     */
    public static final int USER_TYPE = 1;

    /**
     * Internal Type; for example: performance metrics.
     */
    public static final int INTERNAL_TYPE = 2;

    /**
     * Environment Variable Type; for example: Servlet Port #.
     */
    public static final int ENVIRONMENT_TYPE = 3;

    /**
     * HTTP Type; for example: Get vs Post.
     */
    public static final int HTTP_TYPE = 4;

    /**
     * HTTP Header Type;  for example:  Browser Client information.
     */
    public static final int HTTP_HEADER_TYPE = 5;

    /**
     * User Session Type.
     */
    public static final int REQUEST_ATTRIBUTE_TYPE = 6;

    /**
     * User Session Type.
     */
    public static final int SESSION_TYPE = 7;

    /**
     * Servlet Context Type.
     */
    public static final int SERVLET_CONTEXT_TYPE = 8;

    private String name;
    private String value;
    private int type;
    private final String[] paramTypes =
            {"Cookie", "User Parameter", "Internal", "Environment",
                    "HTTP", "HTTP Header", "Internal Request Attribute", "Session",
                    "Servlet Context"};

    private static final String UNDEFINED = "Undefined";

    /**
     * Constructor for String parameter.
     *
     * @param type  parameter type, e.g. COOKIE_TYPE, ENVIRONMENT_TYPE
     * @param name  parameter name
     * @param value parameter String value
     */
    public XDebugParameter(int type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     * Constructor for integer parameter.
     *
     * @param type  parameter type, e.g. COOKIE_TYPE, ENVIRONMENT_TYPE
     * @param name  parameter name
     * @param value parameter integer value
     */
    public XDebugParameter(int type, String name, int value) {
        this.type = type;
        this.name = name;
        this.value = Integer.toString(value);
    }

    /**
     * Constructor for boolean parameter.
     *
     * @param type  parameter type, e.g. COOKIE_TYPE, ENVIRONMENT_TYPE
     * @param name  parameter name
     * @param value parameter String value
     */
    public XDebugParameter(int type, String name, boolean value) {
        this.type = type;
        this.name = name;
        this.value = Boolean.toString(value);
    }

    /**
     * Get Parameter Name.
     *
     * @return name of parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Get Parameter Value.
     *
     * @return value of the parameter
     */
    public String getValue() {
        return value;
    }

    /**
     * Get Parameter Type Name.
     *
     * @return parameter type name, e.g. "Cookie", Internal", etc.
     */
    public String getTypeName() {
        String typeName = null;
        try {
            typeName = paramTypes[type];
        } catch (ArrayIndexOutOfBoundsException e) {
            typeName = UNDEFINED;
        }
        return typeName;
    }

    /**
     * Get Parameter Type.
     *
     * @return parameter type integer code, e.g. COOKIE_TYPE, INTERNAL_TYPE
     */
    public int getType() {
        return type;
    }
}