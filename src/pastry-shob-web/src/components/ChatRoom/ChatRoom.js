import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
const ChatRoom = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [messages, setMessages] = useState(null);
    const [newMessage, setNewMessage] = useState("")
    const navigate = useNavigate();
    const lastMessageRef = useRef(null);
    const [showMessage, setShowMessage] = useState(true);
    const [isVisible, setIsVisible] = useState(true);
    const {t} = useTranslation();

    useEffect(() => {
        if (lastMessageRef.current) {
            lastMessageRef.current.scrollIntoView({behavior: 'smooth'});
        }
    }, [messages]);

    // eslint-disable-next-line react-hooks/exhaustive-deps
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
            if (newMessage.trim() === '') {
                return;
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

    const sendWithEnter = (event) => {
        if (event.keyCode === 13) {
            sentMessage()
        }
    }

    useEffect(() => {
        const interval = setInterval(() => {
            getMessage()
        }, 1000)
        return () => {
            clearInterval(interval)
        }
    }, [getMessage])


    return (
        <main>
            {isVisible ?
                <div className="chat-container" style={{
                    position: 'fixed',
                    right: 0,
                    width: '27%',
                    height: '61%',
                    backgroundColor: 'white',
                    zIndex: '9999',
                    overflow: 'auto',
                    padding: '0px',
                    color: 'black',
                    fontStyle: 'italic',
                    borderRadius: '10px',
                    bottom: 0,
                    marginRight: '50px',
                    border: '2px solid #ef7d00'
                }}>
                    <section className="chat-container-title">
                        <h3>{t('chat-container.h3')}</h3>
                        <a
                            className="chat-container-close"
                            id="submit"
                            type="submit"
                            onClick={handleClickCloseMessage}
                        >X
                        </a>
                    </section>
                    {showMessage &&
                        <div className="chat-container-messages"
                             style={{marginTop: '40px', overflowY: 'scroll', height: '60%'}}>
                            {messages ? (
                                messages.map((message) => (
                                    <div className="chat-container-messages-current"
                                         key={message.id}
                                         ref={lastMessageRef}>
                                        {
                                            message.adminId === null
                                                ?
                                                <p  key={message.id}
                                                    className="chat-container-messages-current-user">
                                                    Вие: {message.message}
                                                </p>
                                                :
                                                <p  key={message.id}
                                                    className="chat-container-messages-current-admin">
                                                    Админ: {message.message}
                                                </p>
                                        }
                                    </div>
                                ))

                            ) : (
                                <p className="chat-container-messages-down">
                                    {t('chat-container.p')}
                                </p>
                            )}
                        </div>
                    }
                    <div className="chat-container-input" style={{marginTop: '20px'}}>
                        <input
                            type="text"
                            name="message"
                            placeholder={t('chat-container.placeholder')}
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            onFocus={handleClickOpenMessage}
                            autoComplete="off"
                            onKeyDown={(e) => sendWithEnter(e)}

                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sentMessage()}
                        >
                            {t('chat-container.button')}
                        </button>
                    </div>
                </div>
                :
                <div className="chat-container" style={{
                    position: 'fixed',
                    right: 0,
                    width: '27%',
                    height: '17%',
                    backgroundColor: 'white',
                    zIndex: '9999',
                    overflow: 'auto',
                    padding: '0px',
                    color: 'black',
                    fontStyle: 'italic',
                    borderRadius: '10px',
                    bottom: 0,
                    marginRight: '50px'
                }}>
                    <section className="chat-container-title">
                        <h3>{t('chat-container.h3')}</h3>
                        <a
                            className="chat-container-close"
                            id="submit"
                            type="submit"
                            onClick={handleClickCloseMessage}
                        >X
                        </a>
                    </section>
                    <div className="chat-container-input" style={{marginTop: '20px'}}>
                        <input
                            type="text"
                            name="message"
                            placeholder={t('chat-container.placeholder')}
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            onFocus={handleClickOpenMessage}
                        />
                        <button
                            id="submit"
                            type="button"
                            onClick={() => sentMessage()}
                        >
                            {t('chat-container.button')}
                        </button>
                    </div>
                </div>
            }
        </main>
    )
}

export default ChatRoom;