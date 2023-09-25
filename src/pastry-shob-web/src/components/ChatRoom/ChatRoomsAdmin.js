import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useState} from "react";

const ChatRoomsAdmin = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [allMessage, setAllMessage] = useState(null);
    const [currentMessage, setCurrentMessage] = useState(null);
    const [showMessage, setShowMessage] = useState(false);

    function getAllMessage() {
        ajax(`${baseUrl}api/chatroom/admin/all`, 'GET', user.jwt)
            .then(response => {
                setAllMessage(response);
            });
    }

    useEffect(() => {
        getAllMessage()
    }, [])

    function getMessageByUser(id) {
        ajax(`${baseUrl}api/chatroom/admin/${id}`, "GET", user.jwt)
            .then(messageResponse => {
                setCurrentMessage(messageResponse)
            });
    }

    return (
        <main>
            {allMessage ? (
                <section className="message-admin-container">
                    {allMessage.map((message) => (
                        <button
                            id={message.userId}
                            key={message.userId}
                            type="button"
                            onClick={() => getMessageByUser(message.userId)}
                        >
                            {message.message}
                        </button>
                    ))}
                </section>
            ) : (
                <></>
            )}
        </main>
    )
}

export default ChatRoomsAdmin;