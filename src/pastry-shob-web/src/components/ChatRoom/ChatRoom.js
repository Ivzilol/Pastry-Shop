import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";

const ChatRoom = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [messages, setMessages] = useState(null);
    const [newMessage, setNewMessage] = useState("")
    const navigate = useNavigate();
    const lastMessageRef = useRef(null);
    const [showMessage, setShowMessage] = useState(true);
    const [isVisible, setIsVisible] = useState(true);

    useEffect(() => {
        if (lastMessageRef.current) {
            lastMessageRef.current.scrollIntoView({behavior: 'smooth'});
        }
    }, [messages]);

    function getMessage() {
        if (user.jwt) {
            ajax(`${baseUrl}api/chatroom`, "GET", user.jwt)
                .then(result => {
                    setMessages(result)
                    setShowMessage(true)
                })
        }
    }

    function sentMessage() {
        if (user.jwt) {
            const requestBody = {
                newMessage: newMessage
            }
            ajax(`${baseUrl}api/chatroom/send`, "POST", user.jwt, requestBody)
                .then((response) => {
                    if (response !== undefined) {
                        getMessage()
                        return null;

                    } else {
                        handleSubmit()
                        getMessage();
                    }

                })
        } else {
            navigate("/login")
        }
    }

    const handleSubmit = () => {
        setNewMessage('');
    }

    function handleClickCloseMessage() {
        setShowMessage(false)
        setIsVisible(false)
    }

    function handleClickOpenMessage() {
        getMessage()
        setShowMessage(true)
        setIsVisible(true)
    }


    return (
        <main>
            {isVisible ?
                <div className="chat-container" style={{
                    position: 'fixed',
                    right: 0,
                    width: '27%',
                    height: '67%',
                    backgroundColor: 'blue',
                    zIndex: '9999',
                    overflow: 'auto',
                    padding: '20px',
                    color: 'black',
                    fontStyle: 'italic',
                    borderRadius: '10px',
                    bottom: 0,
                    marginRight: '50px'
                }}>
                    <section className="chat-container-title">
                        <h3>Пишете ни</h3>
                        <button
                            className="chat-container-close"
                            id="submit"
                            type="submit"
                            onClick={handleClickCloseMessage}
                        >X
                        </button>
                    </section>
                    {showMessage &&
                        <div className="chat-container-messages"
                             style={{marginTop: '40px', overflowY: 'scroll', height: '60%'}}>
                            {messages ? (
                                messages.map((message) => (
                                    <div key={message.id} ref={lastMessageRef}>
                                        <p key={message.id}>{message.message}</p>
                                    </div>
                                ))
                            ) : (
                                <></>
                            )}

                        </div>
                    }
                    <div className="chat-container-input" style={{marginTop: '20px'}}>
                        <input
                            type="text"
                            name="message"
                            placeholder="Напиешете съобщение"
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            onFocus={handleClickOpenMessage}
                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sentMessage()}
                        >
                            Send
                        </button>
                    </div>
                </div>
                :
                <div className="chat-container" style={{
                    position: 'fixed',
                    right: 0,
                    width: '27%',
                    height: '27%',
                    backgroundColor: 'blue',
                    zIndex: '9999',
                    overflow: 'auto',
                    padding: '20px',
                    color: 'black',
                    fontStyle: 'italic',
                    borderRadius: '10px',
                    bottom: 0,
                    marginRight: '50px'
                }}>
                    <section className="chat-container-title">
                        <h3>Пишете ни</h3>
                        <button
                            className="chat-container-close"
                            id="submit"
                            type="submit"
                            onClick={handleClickCloseMessage}
                        >X
                        </button>
                    </section>
                    {showMessage &&
                        <div className="chat-container-messages"
                             style={{marginTop: '40px', overflowY: 'scroll', height: '60%'}}>
                            {messages ? (
                                messages.map((message) => (
                                    <div key={message.id} ref={lastMessageRef}>
                                        <p key={message.id}>{message.message}</p>
                                    </div>
                                ))
                            ) : (
                                <></>
                            )}

                        </div>
                    }
                    <div className="chat-container-input" style={{marginTop: '20px'}}>
                        <input
                            type="text"
                            name="message"
                            placeholder="Напиешете съобщение"
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            onFocus={handleClickOpenMessage}
                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sentMessage()}
                        >
                            Send
                        </button>
                    </div>
                </div>
            }
        </main>
    )
}

export default ChatRoom;