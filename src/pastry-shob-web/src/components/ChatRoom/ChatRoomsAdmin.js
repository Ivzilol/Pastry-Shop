import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useRef, useState} from "react";

const ChatRoomsAdmin = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [allMessage, setAllMessage] = useState(null);
    const [currentMessages, setCurrentMessages] = useState(null);
    const [showMessage, setShowMessage] = useState(false);
    const lastMessageRef = useRef(null);
    const [newMessageAdmin, setNewMessageAdmin] = useState("");
    const [currentId, setCurrentId] = useState(null);

    useEffect(() => {
        if (lastMessageRef.current) {
            lastMessageRef.current.scrollIntoView({behavior: 'smooth'});
        }
    }, [currentMessages]);

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
                setCurrentId(id)
            });
    }


    function sendMessageAdmin(id) {
        const requestBody = {
            newMessageAdmin: newMessageAdmin
        }
        if (newMessageAdmin.trim() === '') {
            return;
        }
        ajax(`${baseUrl}api/chatroom/admin/answer/${id}`, "POST", user.jwt, requestBody)
            .then(response => {
                if (response !== undefined) {
                    getMessageByUser(currentId)
                    handleSubmit()
                } else {
                    handleSubmit()
                    getMessageByUser(currentId);
                }
            })
    }

    const handleSubmit = () => {
        setNewMessageAdmin('');
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
                                ref={lastMessageRef}
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
                        <div className="message-admin-container-current-user-container"
                                style={{marginTop: '40px', overflowY: 'scroll', height: '80%'}}>
                            {currentMessages.map((current) => (
                                <p key={current.message}
                                >{current.message}</p>

                            ))}
                        </div>
                    ) : (
                        <></>
                    )}
                    <div>
                        <input
                            type="text"
                            name="message"
                            placeholder="Напиешете съобщение"
                            value={newMessageAdmin}
                            onChange={(e) => setNewMessageAdmin(e.target.value)}
                            autoComplete="off"
                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sendMessageAdmin(currentId)}
                        >
                            Send
                        </button>
                    </div>
                </section>
            }
        </main>
    )
}

export default ChatRoomsAdmin;