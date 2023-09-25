import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useState} from "react";

const ChatRoomsAdmin = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [allMessage, setAllMessage] = useState(null);
    const [currentMessages, setCurrentMessages] = useState(null);
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
                setCurrentMessages(messageResponse)
                setShowMessage(true)
            });
    }

    return (
        <main className="message-admin-container">
            <section className="message-admin-container-all-messages">
                {allMessage ? (
                    <section className="message-admin-container-buttons">
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
            </section>
            {showMessage &&
                <section className="message-admin-container-current-user">
                    {currentMessages ? (
                        <div className="message-admin-container-current-user-container">
                            {currentMessages.map((current) => (
                                <p key={current.message}
                                >{current.message}</p>
                            ))}
                        </div>
                    ) : (
                        <></>
                    )}
                </section>
            }
        </main>
    )
}

export default ChatRoomsAdmin;