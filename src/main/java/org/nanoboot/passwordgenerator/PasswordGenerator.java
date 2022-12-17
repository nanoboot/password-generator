///////////////////////////////////////////////////////////////////////////////////////////////
// password-generator: Tool generating random password.
// Copyright (C) 2016-2022 the original author or authors.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; version 2
// of the License only.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
///////////////////////////////////////////////////////////////////////////////////////////////

package org.nanoboot.passwordgenerator;

import org.nanoboot.powerframework.view.ViewRunner;
import org.nanoboot.powerframework.view.window.Window;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import org.nanoboot.powerframework.time.moment.UniversalDateTime;
import org.nanoboot.powerframework.random.generators.linearcongruent.combined.w5.W5RandomGenerator;
import org.nanoboot.powerframework.view.EnumColour;
import org.nanoboot.powerframework.view.View;
import org.nanoboot.powerframework.view.layouts.CellLayout;
import org.nanoboot.powerframework.view.window.WindowColourSkin;
import org.nanoboot.powerframework.view.window.controls.Button;
import org.nanoboot.powerframework.view.window.controls.SLabel;
import org.nanoboot.powerframework.view.window.controls.TextField;

/**
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class PasswordGenerator {

    private static class PasswordWindow extends Window {
        private W5RandomGenerator prg;

        private TextField newPasswordTextField;
        private Button generateNewPasswordButton;

        PasswordWindow() {
            this.prg = getPrg();
        }

        private String getNewPassword() {
            return prg.nextText(16);
        }

        private void handleGenerateNewPasswordButtonAction(ActionEvent event) {
            this.setNewPasswordToTheNewPasswordTextField();
        }

        private void setNewPasswordToTheNewPasswordTextField() {
            this.newPasswordTextField.setText(this.getNewPassword());
        }

        @Override
        protected void initAreaForUserInteraction(Object... args) {
            this.setWindowTitle("Password generator");
            this.showOnlyTheCloseButton();
            this.setHeight(400);

            prg = getPrg();

            CellLayout cellLayout = new CellLayout(2, 2, "50% 50%");
            this.applicationArea.getChildren().add(cellLayout);
            SLabel newPasswordLabel = new SLabel("New password");
            newPasswordTextField = new TextField();
            newPasswordTextField.setFont(new Font("Courier New", 24));

            cellLayout.addNodes(1, newPasswordLabel, newPasswordTextField);
            generateNewPasswordButton = new Button("Generate new password");

            cellLayout.addNode(generateNewPasswordButton, 1, 2, 2, 1);

            this.generateNewPasswordButton.setOnAction(this::handleGenerateNewPasswordButtonAction);

            this.setNewPasswordToTheNewPasswordTextField();
        }

        private W5RandomGenerator getPrg() {
            return new W5RandomGenerator(getRandomLongArray());
        }

        private long[] getRandomLongArray() {
            long[] array = new long[8];
            array[0] = W5RandomGenerator.getStaticInstance().nextInt(0, 999999999);
            array[1] = UniversalDateTime.now().getMillisecond();
            array[2] = UniversalDateTime.now().getSecond();
            array[3] = UniversalDateTime.now().getMinute();
            array[4] = UniversalDateTime.now().getHour();
            array[5] = UniversalDateTime.now().getDay();
            array[6] = UniversalDateTime.now().getMonth();
            array[7] = UniversalDateTime.now().getYear();
            return array;
        }
    }

    private static class Runner implements ViewRunner {

        @Override
        public void runApp() {
            new PasswordWindow().show();

        }
    }

    public static void main(String[] args) {
        View.startSimplicity(new Runner(), new WindowColourSkin(EnumColour.getRandom()));

    }
}
