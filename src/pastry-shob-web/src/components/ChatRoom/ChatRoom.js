import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import SockJS from 'sockjs-client';
import {over} from 'stompjs';
import baseURL from "../BaseURL/BaseURL";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode";
import ajax from "../../Services/FetchService";

let stompClient = null;


const ChatRoom = () => {

    const user = useUser();
    const [roles, setRoles] = useState(null);
    const [publicChats, setPublicChats] = useState([]);
    const [oldMessages, setOldMessages] = useState(null);
    const [userData, setUserData] = useState({
        username: '',
        receiverName: '',
        connected: false,
        message: ''
    });

    useEffect(() => {
        const decodeJwt = jwt_decode(user.jwt);
        setRoles(decodeJwt.sub)
    }, [user.jwt])

    function connect() {
        let Sock = new SockJS(`${baseURL}ws`);
        stompClient = over(Sock)
        stompClient.connect({}, onConnected, onError)
    }

    function onConnected() {
        console.log('Свързване успешно установено.')
        setUserData({...userData, "connected": true})
        stompClient.subscribe(`/chat-rooms/${roles}`, onMessageReceived);
        userJoin();
    }

    const userJoin = () => {
        const chatMessage = {
            senderName: roles,
            status: "JOIN"
        }
        stompClient.send(`/api/message/${roles}`, {}, JSON.stringify(chatMessage));
    }

    const onError = (err) => {
        console.log(err);
    }

    function onMessageReceived(payload) {
        const payloadData = JSON.parse(payload.body);
        publicChats.push(payloadData)
        setPublicChats([...publicChats]);
    }

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData, "message": value});
    }

    const sendValue = () => {
        if (stompClient) {
            const chatMessage = {
                senderName: roles,
                message: userData.message,
                status: "MESSAGE"
            }
            if (userData.message.trim() === "") {
                return;
            }
            console.log(chatMessage);
            stompClient.send(`/api/message/${roles}`, {}, JSON.stringify(chatMessage));
            setUserData({...userData, "message": ""})
        }
    }

    function getOldMessage() {
        ajax(`${baseURL}api/chat-room/get-messages/${roles}`, "GET", user.jwt)
            .then((response) => {
                setOldMessages(response);
            })
    }

    useEffect(() => {
        if (roles !== null) {
            connect();
            getOldMessage();
        }
    }, [roles])


    return (
        <main>
            <div className="chat-container">
                {userData.connected ?
                    <div className="chat-box">
                        <div className="chat-content">
                            <ul className="chat-message">
                                {oldMessages !== null ? oldMessages.map((oldMessage) => (
                                    <li key={oldMessage.id}
                                        className="chat-message-row">
                                        <div
                                            className="chat-message-data">
                                            {oldMessage.adminId === null
                                                ?
                                                <>{oldMessage.username}: {oldMessage.message}</>
                                                :
                                                <>Админ: {oldMessage.message}</>
                                            }
                                        </div>
                                    </li>
                                )) : (
                                    <></>
                                )}
                                {publicChats.map((chat, index) => (
                                    <li key={index}
                                        className="chat-message-row">
                                        {chat.message !== null
                                            ?
                                            <div className="chat-message-data">{chat.senderName}: {chat.message}</div>
                                            :
                                            <></>
                                        }
                                    </li>
                                ))}
                            </ul>
                            <div className="send-message">
                                <input
                                    type="text"
                                    className="input-message"
                                    placeholder="Send Message"
                                    value={userData.message}
                                    onChange={handleMessage}
                                />
                                <button
                                    type="button"
                                    className="send-button"
                                    onClick={sendValue}
                                >
                                    send
                                </button>
                            </div>
                        </div>
                    </div>
                    :
                    <></>
                }
            </div>
        </main>


        // <main>
        //     {isVisible ?
        //         <div className="chat-container" style={{
        //             position: 'fixed',
        //             right: 0,
        //             width: '27%',
        //             height: '61%',
        //             backgroundColor: 'white',
        //             zIndex: '9999',
        //             overflow: 'auto',
        //             padding: '0px',
        //             color: 'black',
        //             fontStyle: 'italic',
        //             borderRadius: '10px',
        //             bottom: 0,
        //             marginRight: '50px',
        //             border: '2px solid #ef7d00'
        //         }}>
        //             <section className="chat-container-title">
        //                 <h3>{t('chat-container.h3')}</h3>
        //                 <a
        //                     className="chat-container-close"
        //                     id="submit"
        //                     type="submit"
        //                     onClick={handleClickCloseMessage}
        //                 >X
        //                 </a>
        //             </section>
        //             {showMessage &&
        //                 <div className="chat-container-messages"
        //                      style={{marginTop: '40px', overflowY: 'scroll', height: '60%'}}>
        //                     {messages ? (
        //                         messages.map((message) => (
        //                             <div className="chat-container-messages-current"
        //                                  key={message.id}
        //                                  ref={lastMessageRef}>
        //                                 {
        //                                     message.adminId === null
        //                                         ?
        //                                         <p  key={message.id}
        //                                             className="chat-container-messages-current-user">
        //                                             Вие: {message.message}
        //                                         </p>
        //                                         :
        //                                         <p  key={message.id}
        //                                             className="chat-container-messages-current-admin">
        //                                             Админ: {message.message}
        //                                         </p>
        //                                 }
        //                             </div>
        //                         ))
        //
        //                     ) : (
        //                         <p className="chat-container-messages-down">
        //                             {t('chat-container.p')}
        //                         </p>
        //                     )}
        //                 </div>
        //             }
        //             <div className="chat-container-input" style={{marginTop: '20px'}}>
        //                 <input
        //                     type="text"
        //                     name="message"
        //                     placeholder={t('chat-container.placeholder')}
        //                     value={newMessage}
        //                     onChange={(e) => setNewMessage(e.target.value)}
        //                     onFocus={handleClickOpenMessage}
        //                     autoComplete="off"
        //                     onKeyDown={(e) => sendWithEnter(e)}
        //
        //                 />
        //                 <button
        //                     id="submit"
        //                     type="button"
        //                     onClick={() => sentMessage()}
        //                 >
        //                     {t('chat-container.button')}
        //                 </button>
        //             </div>
        //         </div>
        //         :
        //         <div className="chat-container" style={{
        //             position: 'fixed',
        //             right: 0,
        //             width: '27%',
        //             height: '17%',
        //             backgroundColor: 'white',
        //             zIndex: '9999',
        //             overflow: 'auto',
        //             padding: '0px',
        //             color: 'black',
        //             fontStyle: 'italic',
        //             borderRadius: '10px',
        //             bottom: 0,
        //             marginRight: '50px'
        //         }}>
        //             <section className="chat-container-title">
        //                 <h3>{t('chat-container.h3')}</h3>
        //                 <a
        //                     className="chat-container-close"
        //                     id="submit"
        //                     type="submit"
        //                     onClick={handleClickCloseMessage}
        //                 >X
        //                 </a>
        //             </section>
        //             <div className="chat-container-input" style={{marginTop: '20px'}}>
        //                 <input
        //                     type="text"
        //                     name="message"
        //                     placeholder={t('chat-container.placeholder')}
        //                     value={newMessage}
        //                     onChange={(e) => setNewMessage(e.target.value)}
        //                     onFocus={handleClickOpenMessage}
        //                 />
        //                 <button
        //                     id="submit"
        //                     type="button"
        //                     onClick={() => sentMessage()}
        //                 >
        //                     {t('chat-container.button')}
        //                 </button>
        //             </div>
        //         </div>
        //     }
        // </main>
    )
}

export default ChatRoom;