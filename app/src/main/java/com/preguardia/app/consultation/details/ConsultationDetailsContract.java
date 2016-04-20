package com.preguardia.app.consultation.details;

import com.preguardia.app.data.model.GenericMessage;

import java.io.IOException;

/**
 * @author amouly on 3/11/16.
 */
public interface ConsultationDetailsContract {

    interface View {

        void configureAdapter(String userType);

        void showLoading();

        void hideLoading();

        void showSuccess();

        void showEmptyFieldError();

        void showImagePreview();

        void clearInput();

        void toggleKeyboard();

        void showUserName(String name);

        void showUserDesc(String desc);

        void addItem(GenericMessage item);

        void showMedicActions();

        void showPatientActions();

        void onCloseConsultationClick();

        void onClose();

        void dismissNewMessageNotification();
    }

    interface Presenter {

        void takePicture() throws IOException;

        void sendMessage(String message);

        void sendPicture();

        void loadItems();

        void closeConsultation();

        void stopListener();
    }
}
