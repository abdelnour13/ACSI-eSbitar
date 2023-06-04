package com.example.demo.utils.validators;

import java.util.regex.Pattern;

public class Validator {
    
    private boolean isValid = true;
    private String value;
    private String message ="";
    private String attName;
    private String errorAtt = "";

    public static final String caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String mins = "abcdefghijklmnopqrstuvwxyz";
    public static final String nums = "0123456789";
    public static final String spec = "&@-_$#?!";

    public static final String validMSG = "valid";

    public Validator() {}

    public Validator attribute(String attName,String value) {
        this.value = value;
        this.attName = attName;
        return this;
    }

    public Validator addValidator(Validator validator) {
        if(isValid) {
            isValid = validator.isValid;
            message = validator.message;
            errorAtt = validator.errorAtt;
        }
        return this;
    }

    public Validator notEmpty(String customMessage) {
        if(isValid) {
            isValid = value.length() > 0;
            if(!isValid) {
                message = customMessage;
                errorAtt = attName;
            }
        }
        return this;
    }

    public Validator notEmpty() { 
        return this.notEmpty(attName+" is empty"); 
    }

    public Validator notNull(String customMessage) {
        if(isValid) {
            isValid = value != null;
            if(!isValid) {
                errorAtt = attName;
                message = customMessage;
            }
        }
        return this;
    }

    public Validator notNull() {
        return this.notNull(attName+" is null");
    }

    public Validator length(int l,String customMessage) {
        if(isValid) {
            isValid = value.length() == l;
            if(!isValid) message = customMessage;
        }
        return this;
    }

    public Validator length(int l) {
        return this.length(l,attName+" must be "+l+" characters length ");
    }

    public Validator minLength(int l,String customMessage) {
        if(isValid) {
            isValid = value.length() >= l;
            if(!isValid) {
                message = customMessage;
                errorAtt = attName;
            }
        }
        return this;
    }

    public Validator minLength(int l) {
        return this.minLength(l,attName+" must be at least "+l+" characters long");
    }

    public Validator maxLength(int l,String customMessage) {
        if(isValid) {
            isValid = value.length() <= l;
            if(!isValid) {
                message = customMessage;
                errorAtt = attName;
            }
        }
        return this;
    }

    public Validator maxLength(int l) {
        return this.maxLength(l,attName+" password's length must be less than"+l);
    }

    public Validator containsAll(String charSet,String customMessage) {
        if(isValid) {

            boolean b[] = new boolean[charSet.length()];
            int k = 0;

            for(int i = 0;i < charSet.length();i++) {
                for(int j = 0;j < value.length();j++) {
                    if(value.charAt(i)==charSet.charAt(j)
                        && !b[i]
                    ) {
                        k++;
                        b[i] = true;
                    }
                }
            }
            isValid = k == charSet.length();
            if(!isValid) {
                message = customMessage;
                errorAtt = attName;
            }
        }
        return this;
    }

    public Validator containsAll(String charSet) {
        return this
            .containsAll(charSet, attName+" must contains the follwing characters all : "
                +charSet
            );
    }

    public Validator contains(String charSet, int count,String customMessage) {
        if(isValid) {

            int k = 0;

            for(int i = 0;i < charSet.length();i++) {
                for(int j = 0;j < value.length();j++) {
                    if(charSet.charAt(i)==value.charAt(j)) {
                        k++;
                    }
                    if(k == count) return this;
                }
            }

            isValid = false;
            message = customMessage;
            errorAtt = attName;
        }
        return this;
    }

    public Validator contains(String charSet, int count) {
        return this
            .contains(charSet, count,attName+" must contains at least "+count+" of the following characters "+charSet);
    }

    public Validator contains(String charSet,String customMessage) {
        return this.contains(charSet,1,customMessage);
    }

    public Validator contains(String charSet) {
        return this.contains(charSet,1);
    }

    public Validator containsDistinct(String charSet, int count,String customMessage) {
        if(isValid) {

            boolean b[] = new boolean[charSet.length()];
            int k = 0;

            for(int i = 0;i < charSet.length();i++) {
                for(int j = 0;j < value.length();j++) {
                    if(charSet.charAt(i)==value.charAt(j)
                        && !b[i]
                    ) {
                        k++;
                        b[i] = true;
                    }
                    if(k == count) return this;
                }
            }

            isValid = false;
            message = customMessage;
            errorAtt = attName;
        }
        return this;
    }

    public Validator containsDistinct(String charSet, int count) {
        return this.
            containsDistinct(charSet, count, attName+" must contains at least "+
                (count == 1 ? " one " : count+" of")
            +
            " of the following characters "+charSet);
    }

    public Validator maches(String regEx, String customMessage) {
        if(isValid) {
            isValid = Pattern
                .compile(regEx)
                .matcher(value)
                .matches();
            if(!isValid) {
                this.message = customMessage;  
                errorAtt = attName;
            }     
        }
        return this;
    }

    public Validator maches(String regEx) {
        return this.maches(attName+" doesn't match "+regEx);
    }

    public Validator isEmail() {
        return this
            .maches("^(.+)@(\\S+)$","invalid email adresse");
    }

    public Validator in(String customMessage,String...options) {
        if(isValid) {
            for(String option : options) {
                if(value.equals(option)) {
                    return this;
                }
            }
            isValid = false;
            message = customMessage;
            errorAtt = attName;
        }
        return this;
    }

    public boolean validate() {
        if(isValid) message = Validator.validMSG;
        return this.isValid;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsValide() {
        return this.isValid;
    }

    public String getAttribute() {
        return errorAtt;
    }

}