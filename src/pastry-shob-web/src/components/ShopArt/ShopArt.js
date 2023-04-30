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
                                src="https://i.imgupx.com/zPzpldZI/333679583_1370979030385235_8353098425062243540_n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products"
                                >Баници</a>
                            </div>
                        </div>
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="http://alfa.kachi-snimka.info/images-2021/bfi1677689901o.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products"
                                >Сладки</a>
                            </div>
                        </div>
                    </div>
                    <div className="shop-art-first-picture-row-one">
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.ibb.co/8BjcJQY/333701579-220719253820799-4477163902665921833-n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products"
                                >Козунаци</a>
                            </div>
                        </div>
                        <div className="shop-art-first-picture-row-one-one">
                            <img
                                src="https://i.imgupx.com/VoOSBgnl/343389914_610690820707442_5178648780943661264_n.jpg"
                                alt=""/>
                            <div className="shop-art-first-paragraph">
                                <a href="/products"
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