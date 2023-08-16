import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

const Author = () => {
    return (
        <main className="author">
            <NavBar/>
            <section className="author-container">
                <div className="author-container-items">
                    <div className="author-container-img">
                        <img src="https://i.postimg.cc/nrn81M2p/ivo.jpg" alt="new"/>
                    </div>
                    <div className="author-container-text">

                        <h2 data-testid="Author">Ивайло Иванов Аличков</h2>
                        <p><p>Здравейте! Казвам се Иво и съм авторът на приложението Сладкарницата на Мама. В него съм
                            използвал снимки на сладкишите, които тя приготвя, както и техни рецепти. Създаденото от мен
                            приложение има за цел да ми послужи за портфолио, като макар продуктите, представени в него,
                            да са реални, при поръчка от приложението няма да има реална доставка, въпреки че то
                            проследява различните цикли на поръчка/доставка на продукта.</p>
                            <p>Малко информация за мен:
                                Изучавам софтуерно инженерство повече от година, като приложението, което виждате, е
                                първият ми по-сериозен проект. Пълното му описание можете да намерите, като последвате
                                линка към репозиторито в Github: <a href="https://github.com/Ivzilol/Pastry-Shop" target="_blank">Тук</a> Накратко, в създаването на
                                проекта съм използвал React за front-end частта и Spring за back-end-a, базата данни е
                                MySQL. Ако желаете да се свържете с мен, можете да ме откриете на посочените във
                                footer-a e-mail или LinkedIn, както и да ми оставите съобщение в секцията с коментари в
                                самото приложение.
                            </p>
                            <p>Поздарви,</p>
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