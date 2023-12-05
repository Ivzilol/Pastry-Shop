import {useUser} from "../../UserProvider/UserProvider";
import React, {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";

const UserChangePassword = () => {
    const user = useUser();
    const [oldPassword, setOldPassword] = useState("")
    const [newPassword, setNewPassword] = useState("")
    const [confirmNewPassword, setConfirmNewPassword] = useState("")
    const [showPassword, setShowPassword] = useState(false);
    const [errorChangePassword, setErrorChangePassword] = useState(false);
    const {t} = useTranslation();

    const [changeError] = useState({
        oldPasswordError: null,
        newPasswordError: null
    })

    function changePassword() {
        const requestBody = {
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        }
        ajax(`${baseURL}api/users/change-password`, "PATCH", user.jwt, requestBody)
            .then((response) => {
                if (response.custom === 'Successful change your password') {
                    return alert("You have successfully changed your password, please login with your new password"),
                        user.setJwt(null),
                        window.location.href = "/login"
                } else {
                    handleSubmit()
                    if (response.custom === 'Old password not match!') {
                        changeError.oldPasswordError = response.custom
                    } else {
                        changeError.newPasswordError = response.custom
                    }
                    setErrorChangePassword(true);

                }
            })
    }

    const handleSubmit = () => {
        setOldPassword("");
        setNewPassword("");
        setConfirmNewPassword("");
    }

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    function closeDialog() {
        setErrorChangePassword(false)
    }

    return (
        <main className="user-change-pass">
            <NavBar/>
            <section className="user-change-pass-container">
                <h1 className="change-password">{t('user-change-pass.title')}</h1>
                <label
                    htmlFor="oldPassword"
                >
                    {t('user-change-pass.label1')}
                </label>
                <input
                    type={showPassword ? "text" : "password"}
                    id="oldPassword"
                    name="oldPassword"
                    placeholder="Old Password"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                    onFocus={closeDialog}
                />
                {errorChangePassword && changeError.oldPasswordError !== null &&
                    <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {changeError.oldPasswordError}</span>
                }
                <a className="forgotten-password-send-show-password"
                   onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                <label
                    htmlFor="oldPassword"
                >
                    {t('user-change-pass.label2')}
                </label>
                <input
                    type={showPassword ? "text" : "password"}
                    id="newPassword"
                    name="newPassword"
                    placeholder="New Password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    onFocus={closeDialog}
                />
                <a className="forgotten-password-send-show-password"
                   onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                <label
                    htmlFor="oldPassword"
                >
                    {t('user-change-pass.label3')}
                </label>
                <input
                    type={showPassword ? "text" : "password"}
                    id="confirmNewPassword"
                    name="confirmNewPassword"
                    placeholder="Confirm New Password"
                    value={confirmNewPassword}
                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                    onFocus={closeDialog}
                />
                {errorChangePassword && changeError.newPasswordError !== null &&
                    <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {changeError.newPasswordError}</span>
                }
                <a className="forgotten-password-send-show-password"
                   onClick={togglePasswordVisibility}>
                    {showPassword ? t('login.hide-password') : t('login.show-password')}
                </a>
                <button
                    id="submit"
                    type="button"
                    onClick={() => changePassword()}
                >
                    {t('user-change-pass.button')}
                </button>
            </section>
        </main>
    )
}

export default UserChangePassword;