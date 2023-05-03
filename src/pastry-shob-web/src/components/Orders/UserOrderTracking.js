import {useUser} from "../../UserProvider/UserProvider";
import {useState} from "react";
import NavBar from "../NavBar/NavBar";

const UserOrderTracking = () => {

    const user = useUser();
    const [order, setOrder] = useState(null);

    return (

        <main className="tracking-main">
            <NavBar/>

        </main>
    )
}

export default UserOrderTracking;