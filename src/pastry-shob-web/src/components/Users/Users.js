import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const UsersAdmin = () => {
    const user = useUser();
    const [users, setUsers] = useState(null);

    useEffect(() => {
        ajax("/api/users", "GET", user.jwt)
            .then(userData => {
                setUsers(userData);
            })

    }, [user.jwt])

    return (
        <main>

        </main>
    )
}

export default UsersAdmin;