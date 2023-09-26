import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useRef, useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";

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

    function finishChat(id) {
        ajax(`${baseUrl}api/chatroom/admin/finish/${id}`, "PATCH", user.jwt, null)
            .then(response => {

            })
    }

    function handleClickOpenMessage() {
        getMessageByUser(currentId)
        setShowMessage(true)
    }

    useEffect(() => {
        const interval = setInterval(() => {
            getMessageByUser(currentId)
        }, 1000)
        return () => {
            clearInterval(interval)
        }
    }, [currentId, getMessageByUser])

    return (
        <main className="message-admin-container">
            <NavBarAdmin/>
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
                                <div className="chat-container-messages-current"
                                     key={current.id}
                                     ref={lastMessageRef}>
                                    {
                                        current.adminId === null
                                            ?
                                            <p  key={current.id}
                                                className="chat-container-messages-current-user">
                                                Потребител: {current.message}
                                            </p>
                                            :
                                            <p  key={current.id}
                                                className="chat-container-messages-current-admin">
                                                Вие: {current.message}
                                            </p>
                                    }
                                </div>
                            ))}
                        </div>
                    ) : (
                        <></>
                    )}
                    <div className="message-admin-container-current-user-container-input">
                        <input
                            type="text"
                            name="message"
                            placeholder="Напиешете съобщение"
                            value={newMessageAdmin}
                            onChange={(e) => setNewMessageAdmin(e.target.value)}
                            onFocus={handleClickOpenMessage}
                            autoComplete="off"
                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sendMessageAdmin(currentId)}
                        >
                            Send
                        </button>
                        <button
                            id="submit"
                            type="button"
                            onClick={() =>  finishChat(currentId)}
                        >
                            Answered
                        </button>
                    </div>
                </section>
            }
        </main>
    )
}

export default ChatRoomsAdmin;