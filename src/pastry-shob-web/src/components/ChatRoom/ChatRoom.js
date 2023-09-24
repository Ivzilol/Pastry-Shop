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
    }

    function handleClickOpenMessage() {
        getMessage()
        setShowMessage(true)
    }


    return (
        <div className="chat-container">
            <h3>Пишете ни</h3>
            <a
                className="close"
                id="submit"
                type="submit"
                onClick={handleClickCloseMessage}
            >X</a>
            {showMessage &&
                <div className="chat-container-messages"
                     style={{marginTop: '40px', overflowY: 'scroll', height: '60%'}}>
                    {messages ? (
                        messages.map((message) => (
                            <div key={message.id} ref={lastMessageRef}>
                                <p>{message.message}</p>
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
    )
}

export default ChatRoom;