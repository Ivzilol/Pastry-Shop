import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import baseURL from "../BaseURL/BaseURL";


const Monitoring = () => {

    const user = useUser();

    function getMonitoring() {
        ajax(`${baseURL}api/monitoring`, "GET", user.jwt)
            .then((response) => {
                console.log(response);
            })
    }

    return (
        <><button
            id="submit"
            type="submit"
            onClick={() => getMonitoring()}
        >
            proba
        </button></>
    )
}

export default Monitoring;