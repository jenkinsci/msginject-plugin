/*
 * The MIT License
 *
 * Copyright (c) 2016 Thanh Ha. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.plugins.msginject;

import hudson.Extension;
import hudson.model.Descriptor;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * This class is used to handle the global configuration of the MsgInject
 * plugin.
 *
 * @author Thanh Ha
 */
@Extension
public class MsgInjectConfig extends GlobalConfiguration {

    private String msg;  // message to inject into Gerrit Trigger comment

    public MsgInjectConfig() {
        load();
    }

    public static MsgInjectConfig get() {
        return GlobalConfiguration.all().get(MsgInjectConfig.class);
    }

    /*
    * This method runs when user saves the configuration form
    */
    @Override
    public boolean configure(StaplerRequest req, JSONObject json)
            throws Descriptor.FormException {
        msg = json.getString("msg");
        req.bindJSON(this, json);
        save();
        return true;
    }

    /**
     * This method returns the value from the msg
     */
    public String getMsg() {
        return msg;
    }
}
