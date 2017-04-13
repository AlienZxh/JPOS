package com.j1j2.jposmvvm;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by alienzxh on 16-5-4.
 */
public class JPOSApplication extends TinkerApplication {

    public JPOSApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                "com.j1j2.jposmvvm.JPOSApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    public JPOSApplication(int tinkerFlags, String delegateClassName, String loaderClassName, boolean tinkerLoadVerifyFlag) {
        super(tinkerFlags, delegateClassName, loaderClassName, tinkerLoadVerifyFlag);
    }

}
