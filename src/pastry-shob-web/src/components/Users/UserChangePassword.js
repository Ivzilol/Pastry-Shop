import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";

const UserChangePassword = () => {
    const user = useUser();
    const [oldPassword, setOldPassword] = useState("")
    const [newPassword, setNewPassword] = useState("")
    const [confirmNewPassword, setConfirmNewPassword] = useState("")
    const {t} = useTranslation();
    const baseUrl = "http://localhost:8080/";

    function changePassword() {
        const requestBody = {
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        }
        ajax(`${baseUrl}api/users/change-password`, "PATCH", user.jwt, requestBody)
            .then((response) => {
                console.log(response);
                if (response !== undefined) {
                    return alert("You have successfully changed your password, please login with your new password"),
                        user.setJwt(null),
                        window.location.href = "/login"
                } else {
                    return alert("Password change failed")
                }
            })
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
                    type="password"
                    id="oldPassword"
                    name="oldPassword"
                    placeholder="Old Password"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                />
                <label
                    htmlFor="oldPassword"
                >
                    {t('user-change-pass.label2')}
                </label>
                <input
                    type="password"
                    id="newPassword"
                    name="newPassword"
                    placeholder="New Password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <label
                    htmlFor="oldPassword"
                >
                    {t('user-change-pass.label3')}
                </label>
                <input
                    type="password"
                    id="confirmNewPassword"
                    name="confirmNewPassword"
                    placeholder="Confirm New Password"
                    value={confirmNewPassword}
                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                />
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