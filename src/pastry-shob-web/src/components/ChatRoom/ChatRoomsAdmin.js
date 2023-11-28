import {useUser} from "../../UserProvider/UserProvider";
import ajax from "../../Services/FetchService";
import {useEffect, useState} from "react";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";

const ChatRoomsAdmin = () => {

    const user = useUser();
    const [allMessage, setAllMessage] = useState(null);

    useEffect(() => {
        ajax(`${baseURL}api/chat-room/admin/all`, 'GET', user.jwt)
            .then(response => {
                setAllMessage(response);
            });
    }, [])


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
                                type="button"
                                onClick={() => {
                                    window.location.href = `/chat-room/${message.username}`
                                }}
                            >
                                {message.username}
                            </button>
                        ))}
                    </section>
                ) : (
                    <></>
                )}
            </section>
        </main>
    )
}

export default ChatRoomsAdmin;