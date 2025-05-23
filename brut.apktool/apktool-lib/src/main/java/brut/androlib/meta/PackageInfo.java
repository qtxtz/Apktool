/*
 *  Copyright (C) 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2010 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.androlib.meta;

import brut.yaml.*;

public class PackageInfo implements YamlSerializable {
    private String mForcedPackageId;
    private String mRenameManifestPackage;

    public PackageInfo() {
        clear();
    }

    public void clear() {
        mForcedPackageId = null;
        mRenameManifestPackage = null;
    }

    public boolean isEmpty() {
        return mForcedPackageId == null && mRenameManifestPackage == null;
    }

    @Override
    public void readItem(YamlReader reader) {
        YamlLine line = reader.getLine();
        switch (line.getKey()) {
            case "forcedPackageId": {
                mForcedPackageId = line.getValue();
                break;
            }
            case "renameManifestPackage": {
                mRenameManifestPackage = line.getValue();
                break;
            }
        }
    }

    @Override
    public void write(YamlWriter writer) {
        if (mForcedPackageId != null) {
            writer.writeString("forcedPackageId", mForcedPackageId);
        }
        if (mRenameManifestPackage != null) {
            writer.writeString("renameManifestPackage", mRenameManifestPackage);
        }
    }

    public String getForcedPackageId() {
        return mForcedPackageId;
    }

    public void setForcedPackageId(String forcedPackageId) {
        mForcedPackageId = forcedPackageId;
    }

    public String getRenameManifestPackage() {
        return mRenameManifestPackage;
    }

    public void setRenameManifestPackage(String renameManifestPackage) {
        mRenameManifestPackage = renameManifestPackage;
    }
}
