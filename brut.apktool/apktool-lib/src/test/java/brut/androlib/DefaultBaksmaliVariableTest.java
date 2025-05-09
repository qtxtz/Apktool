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
package brut.androlib;

import brut.common.BrutException;
import brut.directory.ExtFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.*;
import static org.junit.Assert.*;

public class DefaultBaksmaliVariableTest extends BaseTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        sTestOrigDir = new ExtFile(sTmpDir, "issue1481-orig");
        sTestNewDir = new ExtFile(sTmpDir, "issue1481-new");

        LOGGER.info("Unpacking issue1481...");
        TestUtils.copyResourceDir(DefaultBaksmaliVariableTest.class, "issue1481", sTestOrigDir);

        LOGGER.info("Building issue1481.jar...");
        ExtFile testJar = new ExtFile(sTmpDir, "issue1481.jar");
        new ApkBuilder(sTestOrigDir, sConfig).build(testJar);

        LOGGER.info("Decoding issue1481.jar...");
        new ApkDecoder(testJar, sConfig).decode(sTestNewDir);
    }

    @Test
    public void confirmBaksmaliParamsAreTheSame() throws IOException {
        String expected = ".class public final Lcom/ibotpeaches/issue1481/BuildConfig;\n"
                + ".super Ljava/lang/Object;\n"
                + ".source \"BuildConfig.java\"\n"
                + "\n"
                + "\n"
                + "# static fields\n"
                + ".field public static final APPLICATION_ID:Ljava/lang/String; = \"com.ibotpeaches.issue1481\"\n"
                + "\n"
                + ".field public static final BUILD_TYPE:Ljava/lang/String; = \"debug\"\n"
                + "\n"
                + ".field public static final DEBUG:Z\n"
                + "\n"
                + ".field public static final FLAVOR:Ljava/lang/String; = \"\"\n"
                + "\n"
                + ".field public static final VERSION_CODE:I = 0x1\n"
                + "\n"
                + ".field public static final VERSION_NAME:Ljava/lang/String; = \"1.0\"\n"
                + "\n"
                + "\n"
                + "# direct methods\n"
                + ".method static constructor <clinit>()V\n"
                + "    .locals 1\n"
                + "\n"
                + "    .prologue\n"
                + "    .line 7\n"
                + "    const-string v0, \"true\"\n"
                + "\n"
                + "    invoke-static {v0}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z\n"
                + "\n"
                + "    move-result v0\n"
                + "\n"
                + "    sput-boolean v0, Lcom/ibotpeaches/issue1481/BuildConfig;->DEBUG:Z\n"
                + "\n"
                + "    return-void\n"
                + ".end method\n"
                + "\n"
                + ".method public constructor <init>()V\n"
                + "    .locals 0\n"
                + "\n"
                + "    .prologue\n"
                + "    .line 6\n"
                + "    invoke-direct {p0}, Ljava/lang/Object;-><init>()V\n"
                + "\n"
                + "    return-void\n"
                + ".end method";

        File smali = new File(sTestNewDir, "smali/com/ibotpeaches/issue1481/BuildConfig.smali");
        String obtained = new String(Files.readAllBytes(smali.toPath()));

        assertEquals(TestUtils.replaceNewlines(expected), TestUtils.replaceNewlines(obtained));
    }
}
