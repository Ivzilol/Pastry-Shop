import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const ChatRoom = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [message, setMessage] = useState(null);


    function getMessage() {
        if (user.jwt) {
            ajax(`${baseUrl}api/chatroom`, "GET", user.jwt)
                .then(result => {
                    setMessage(result)
                })
        }
    }

    useEffect(() => {
        getMessage()
    }, []);

    function sentMessage() {
        if (user.jwt) {
        }
    }


    return (
        <div>
            Proba
        </div>
    )
}

export default ChatRoom;