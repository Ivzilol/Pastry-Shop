import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const UserChangePassword = () => {
    const user = useUser();
    const [oldPassword, setOldPassword] = useState("")
    const [newPassword, setNewPassword] = useState("")
    const [confirmNewPassword, setConfirmNewPassword] = useState("")
    const baseUrl = "http://localhost:8080/";

    function changePassword(){
        const requestBody = {
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        }
        ajax(`${baseUrl}api/users/change-password`, "PATCH", user.jwt, requestBody)
            .then()
    }

    return(
        <main>
            <NavBar/>
            <h1 className="change-password">Change Password</h1>
            <label
                htmlFor="oldPassword"
            >
                Old Password
            </label>
            <input
                type="text"
                id="oldPassword"
                name="oldPassword"
                placeholder="oldPassword"
                value={oldPassword}
                onChange={(e) => setOldPassword(e.target.value)}
            />
            <label
                htmlFor="oldPassword"
            >
                New Password
            </label>
            <input
                type="text"
                id="newPassword"
                name="newPassword"
                placeholder="newPassword"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
            />
            <label
                htmlFor="oldPassword"
            >
                Confirm New Password
            </label>
            <input
                type="text"
                id="confirmNewPassword"
                name="confirmNewPassword"
                placeholder="confirmNewPassword"
                value={confirmNewPassword}
                onChange={(e) => setConfirmNewPassword(e.target.value)}
            />
            <button
                id="submit"
                type="button"
                onClick={() => changePassword()}
            >
                Change Password
            </button>
        </main>
    )
}

export default UserChangePassword;