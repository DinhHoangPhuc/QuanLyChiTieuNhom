package com.quanlychitieunhom.Uitls;

import java.util.Map;

public class OperationError {
    private final OperationErrorType errorType;
    private final String messageTitle;
    private final String messageContent;
    private final Map<String, Object> fieldErrors;

    private OperationError(Builder builder) {
        this.errorType = builder.errorType;
        this.messageTitle = builder.messageTitle;
        this.messageContent = builder.messageContent;
        this.fieldErrors = builder.fieldErrors;
    }

    public static class Builder {
        private OperationErrorType errorType;
        private String messageTitle;
        private String messageContent;
        private Map<String, Object> fieldErrors;

        public Builder errorType(OperationErrorType errorType) {
            this.errorType = errorType;
            return this;
        }

        public Builder messageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
            return this;
        }

        public Builder messageContent(String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public Builder fieldErrors(Map<String, Object> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public OperationError build() {
            return new OperationError(this);
        }
    }
}
