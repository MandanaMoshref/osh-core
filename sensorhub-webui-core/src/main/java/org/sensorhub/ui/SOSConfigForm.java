/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package org.sensorhub.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import org.sensorhub.ui.data.MyBeanItem;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;


public class SOSConfigForm extends GenericConfigForm
{
    private static final long serialVersionUID = -5570947777524310604L;
    protected static final String SOS_PACKAGE = "org.sensorhub.impl.service.sos.";
    protected static final String PROP_DATAPROVIDERS = "dataProviders";
    protected static final String PROP_DATACONSUMERS = "dataConsumers";
    protected static final String PROP_ENDPOINT = "endPoint";
    
    
    @Override
    public void build(String title, String popupText, MyBeanItem<? extends Object> beanItem, boolean includeSubForms)
    {
        super.build(title, popupText, beanItem, includeSubForms);
        
        // add link to capabilities
        Property<?> endPointProp = beanItem.getItemProperty(PROP_ENDPOINT);
        if (endPointProp != null)
        {
            String baseUrl = (String)endPointProp.getValue();
            if (baseUrl != null)
            {
                baseUrl = baseUrl.substring(1);
                String href = baseUrl + "?service=SOS&version=2.0&request=GetCapabilities";
                Link link = new Link("Link to capabilities", new ExternalResource(href), "_blank", 0, 0, null);
                this.addComponent(link);
            }
        }
    }


    @Override
    public boolean isFieldVisible(String propId)
    {
        if (propId.equals(PROP_DATACONSUMERS))
            return false;
        
        return super.isFieldVisible(propId);
    }


    @Override
    public Map<String, Class<?>> getPossibleTypes(String propId)
    {
        if (propId.equals(PROP_DATAPROVIDERS))
        {
            Map<String, Class<?>> classList = new LinkedHashMap<String, Class<?>>();
            try
            {
                classList.put("Sensor Data Source", Class.forName(SOS_PACKAGE + "SensorDataProviderConfig"));
                classList.put("Stream Process Data Source", Class.forName(SOS_PACKAGE + "StreamProcessProviderConfig"));
                classList.put("Storage Data Source", Class.forName(SOS_PACKAGE + "StorageDataProviderConfig"));                
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            return classList;
        }
        
        return super.getPossibleTypes(propId);
    }
}
