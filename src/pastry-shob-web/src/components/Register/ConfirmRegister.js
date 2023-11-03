import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import ajax from "../../Services/FetchService";
import baseURL from "../BaseURL/BaseURL";

const ConfirmRegister = () => {
    const {verificationCode} = useParams();
    let navigate = useNavigate();


    useEffect(() => {
        ajax(`${baseURL}api/users/register/verify/${verificationCode}`, "POST", null, verificationCode)
            .then(response => {
                if (response.custom === 'Successful activate your profile') {
                    alert(response.custom);
                }
            })
    }, [verificationCode])

    return (
        <main className="validation-form">
            <h2>You have successfully activated your account</h2>
            <h6>Please login with your account</h6>
            <button
                onClick={() => {
                    navigate("/login")
                }}
            >Login
            </button>
        </main>
    )
}
export default ConfirmRegister;