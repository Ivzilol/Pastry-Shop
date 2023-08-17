const ShopArt = () => {
    return (
        <main className="shop-art">
            <section className="shop-art-main">
                <h1 className="shop-art-main-title">Добре дошли в Сладкарницата на Мама</h1>
                <h3 className="shop-art-main-town">София</h3>
                <article className="shop-art-first-picture">
                    <div className="shop-art-first-picture-row-one">
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.ibb.co/QM6sBZh/333679583-1370979030385235-8353098425062243540-n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products/pies"
                                >Баници</a>
                            </div>
                        </div>
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.ibb.co/vDRjrkc/bfi1677689901o.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products/sweets"
                                >Сладки</a>
                            </div>
                        </div>
                    </div>
                    <div className="shop-art-first-picture-row-one">
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.ibb.co/NZTYzkG/333787876-951344779329426-826433626994761451-n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products/buns"
                                >Кифли</a>
                            </div>
                        </div>
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.ibb.co/K0JfVSp/343389914-610690820707442-5178648780943661264-n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products/cake"
                                >Торти</a>
                            </div>
                        </div>
                    </div>
                </article>
            </section>
        </main>
    )
}

export default ShopArt;