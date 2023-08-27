import {useParams} from "react-router-dom";
import {useEffect} from "react";
import ajax from "../../Services/FetchService";

const ConfirmRegister = () => {
    const {verificationCode} = useParams();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/users/register/verify/${verificationCode}`, "POST", null, verificationCode)
            .then()
    }, [verificationCode])

    return(
        <main>You have successfully activated your account</main>
    )
}
export default ConfirmRegister;