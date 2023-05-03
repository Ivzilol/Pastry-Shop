import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

const Author = () => {
    return (
        <main className="author">
            <NavBar/>
            <section className="author-container">
                <div className="author-container-items">
                    <div className="author-container-img">
                        <img src="https://i.imgupx.com/BJeqpcTC/ivo.jpg" alt="new"/>
                    </div>
                    <div className="author-container-text">
                        <h2>Ивайло Иванов Аличков</h2>
                        <p><p>Здравйте, казвам се Иво и съм автора на приложението на Мама,
                            направих приложението по рецептите и снимките на майка с цел портфолио.
                            Продуктите, представени в приложението са реални но за съжаление, ако ги поръчате
                            няма да има реална доставка, въпреки че приложението проследява различните
                            цикли на доставка на продукта.</p>
                            <p>Малко информация за мен:
                                Обучавам се за софтуарен инжинер от повече от година и тоав е първия ми по
                                сериозен проект, пълно описание на проекта, може да откриете в линка с github
                                репозиторито на проекта. Накратко използвам React за front-end и Spring за Back-end,
                                базата данни е MySQL.
                                Ако желаете да се свържете с мен, можете да ме откриете на посочените във футера
                                имейл, Linkedin или да ми оставите съобщение в секцията с коментарите в самото
                                приложение.</p>
                            <p>Поздарви</p>
                            <p>Иво</p>
                        </p>
                    </div>
                </div>
            </section>
            <Footer/>
        </main>
    )
}
export default Author