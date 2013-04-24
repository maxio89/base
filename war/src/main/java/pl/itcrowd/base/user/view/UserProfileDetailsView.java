package pl.itcrowd.base.user.view;

import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.util.ImageScaling;
import pl.itcrowd.base.web.BundleKeys;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.richfaces.event.FileUploadEvent;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.util.ImageScaling;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ConversationScoped
public class UserProfileDetailsView implements Serializable {

    private Conversation conversation;

    private boolean edit = false;

    private ImageScaling imageScaling;

    private Logger logger;

    private boolean markModified;

    private Messages messages;

    private User user;

    private UserHome userHome;

    private static final int MAX_FILE_SIZE = 200;

    private static final int AVATAR_SIZE = 200;

    private String fileUploadMessage;

    private byte[] avatar;

    @SuppressWarnings("UnusedDeclaration")
    public UserProfileDetailsView()
    {
    }

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    public UserProfileDetailsView(Conversation conversation, @CurrentUser User currentUser, UserHome userHome, Messages messages, ImageScaling imageScaling, Logger logger)
    {
        this.conversation = conversation;
        this.messages = messages;
        this.userHome = userHome;
        userHome.setId(currentUser.getId());
        this.user = userHome.getInstance();
        this.imageScaling = imageScaling;
        this.logger = logger;
    }

    public boolean getEdit()
    {
        return edit;
    }

    public User getUser()
    {
        return user;
    }

    public void photoUploading(FileUploadEvent event)
    {
        formChanged();
        byte[] data = event.getUploadedFile().getData();
        if (data.length >= MAX_FILE_SIZE) {
            fileUploadMessage = messages.info(BundleKeys.MAX_SIZE_WARNING_AVATAR).build().getText();
            return;
        }
        fileUploadMessage = null;
        try {
            avatar = imageScaling.scale(data, AVATAR_SIZE, AVATAR_SIZE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void cancel()
    {
        edit = false;
//        avatar = getUser().getAvatar();
    }

    public void editAndSave()
    {
        if (edit) {
            userHome.update();
            messages.info(BundleKeys.PROFILE_UPDATED);
            if (!conversation.isTransient()) {
                conversation.end();
            }
            edit = false;
        } else {
            init();
            edit = true;
        }
    }

    public void formChanged()
    {
        markModified = true;
    }

    public void init()
    {
//        avatar = user.getAvatar();

        if (conversation.isTransient())
        {
            conversation.begin();
        }
    }

    public boolean isMarkModified()
    {
        return markModified;
    }

    public void remove()
    {
        avatar = null;
    }

}
