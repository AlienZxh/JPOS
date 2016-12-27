package com.j1j2.jposmvvm;

import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by alienzxh on 16-8-6.
 */
public class JPOSDebugApplication extends JPOSApplication {

    public JPOSDebugApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.j1j2.jposmvvm.JPOSDebugApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
