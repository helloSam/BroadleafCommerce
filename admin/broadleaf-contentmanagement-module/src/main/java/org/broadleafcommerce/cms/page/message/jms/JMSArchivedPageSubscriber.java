/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.cms.page.message.jms;

import org.broadleafcommerce.cms.page.service.PageService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Receives JMS message with a String that indicates the cache key
 * to invalidate.
 *
 * @author bpolster
 */
public class JMSArchivedPageSubscriber implements MessageListener {

    @Resource(name = "blPageService")
    private PageService pageService;

    /*
     * (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        String basePageCacheKey = null;
        try {
            basePageCacheKey = ((TextMessage) message).getText();
            pageService.removePageFromCache(basePageCacheKey);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
