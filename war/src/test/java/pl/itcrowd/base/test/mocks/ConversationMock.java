package pl.itcrowd.base.test.mocks;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Alternative
@ConversationScoped
public class ConversationMock implements Conversation, Serializable {
// ------------------------------ FIELDS ------------------------------

    private static Set<String> activeConversations = new HashSet<String>();

    private static long conversationIdCounter = 1;

    private BeanManager beanManager;

    private String id;

    private boolean isTransient;

    private long timeout;

// --------------------------- CONSTRUCTORS ---------------------------

    public ConversationMock()
    {
    }

    @Inject
    public ConversationMock(@SuppressWarnings("CdiInjectionPointsInspection") BeanManager beanManager)
    {
        this.beanManager = beanManager;
        this.isTransient = true;
        this.timeout = 0;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public long getTimeout()
    {
        verifyConversationContextActive();
        return timeout;
    }

    public void setTimeout(long timeout)
    {
        verifyConversationContextActive();
        this.timeout = timeout;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString()
    {
        if (isTransient) {
            return "Transient conversation";
        } else {
            return "Conversation with id: " + id;
        }
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Conversation ---------------------

    public void begin()
    {
        verifyConversationContextActive();
        if (!isTransient) {
            throw new IllegalStateException("BEGIN_CALLED_ON_LONG_RUNNING_CONVERSATION");
        }
        isTransient = false;
        if (this.id == null) {
            // This a conversation that was made transient previously in this request
            this.id = "" + conversationIdCounter++;
            activeConversations.add(this.id);
        }
    }

    public void begin(String conversationId)
    {
        verifyConversationContextActive();
        if (!isTransient) {
            throw new IllegalStateException("BEGIN_CALLED_ON_LONG_RUNNING_CONVERSATION");
        }
        if (activeConversations.contains(conversationId)) {
            throw new IllegalStateException("CONVERSATION_ID_ALREADY_IN_USE:" + conversationId);
        }
        isTransient = false;
        this.id = conversationId;
    }

    public void end()
    {
        if (isTransient) {
            throw new IllegalStateException("END_CALLED_ON_TRANSIENT_CONVERSATION");
        }
        isTransient = true;
        activeConversations.remove(this.id);
    }

    public String getId()
    {
        verifyConversationContextActive();
        if (!isTransient) {
            return id;
        } else {
            return null;
        }
    }

    public boolean isTransient()
    {
        verifyConversationContextActive();
        return isTransient;
    }

    @PreDestroy
    private void onDestroy()
    {
        activeConversations.remove(this.id);
    }

    private void verifyConversationContextActive()
    {
        try {
            beanManager.getContext(ConversationScoped.class);
        } catch (ContextNotActiveException e) {
            throw new ContextNotActiveException("Conversation Context not active when method called on conversation " + this, e);
        }
    }
}
