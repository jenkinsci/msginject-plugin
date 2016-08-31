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

import java.io.IOException;
import java.lang.InterruptedException;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.Util;

import com.sonyericsson.hudson.plugins.gerrit.trigger.gerritnotifier.GerritMessageProvider;

/**
 * ExtensionPoint that allows us to send custom messages to Gerrit.
 *
 * @author Thanh Ha
 */
@Extension(optional = false)
public class GerritMessageInjector extends GerritMessageProvider {

    @Override
    public String getBuildCompletedMessage(Run build) {
        StringBuilder customMessage = new StringBuilder();
        if (build != null) {
            EnvVars env = new EnvVars();
            try {
                env = build.getEnvironment(TaskListener.NULL);
            } catch(IOException|InterruptedException e) {
                e.printStackTrace();
            }

            if (customMessage.length() > 0) {
                customMessage.append("\n\n");
            }

            String msg = MsgInjectConfig.get().getMsg();
            String injectmsg = Util.replaceMacro(msg, env);
            customMessage.append(injectmsg);

            if (customMessage.length() > 0) {
                return customMessage.toString().replaceAll("'", "\\'");
            }
        }
        return null;
    }
}
