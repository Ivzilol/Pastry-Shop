import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useState} from "react";

const ChatRoomsAdmin = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [allMessage, setAllMessage] = useState(null);

    function getAllMessage() {
        ajax(`${baseUrl}api/chatroom/admin/all`, 'GET', user.jwt)
            .then(response => {
                setAllMessage(response);
            });
    }

    useEffect(() => {
        getAllMessage()
    }, [])

    return (
        <main>Proba</main>
    )
}

export default ChatRoomsAdmin;