import hero from '../assets/hero-img-2.jpg';

function Hero() {
    return (
        <div>
            <div className='flex w-10/12 m-auto' >
                <div className="w-5/12 py-8" >
                    <div className="w-full" >
                        <h1 className="text-5xl font-bold" >
                            <span>
                                You Health Is
                            </span><br/>
                            <span className="text-c-blue-950" >
                                Our Priority
                            </span>
                        </h1>
                        <p className="py-2 text-black" >
                            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Id cumque ut corporis aliquam ex qui iure quo, magni totam ea error aut, labore cum. Facere quos aspernatur ratione enim quibusdam?
                        </p>
                    </div>
                    <button className="text-c-blue-950 p-3 border-2 border-c-blue-950
                        hover:bg-c-blue-950 hover:text-white
                    " >
                        sign up from here
                    </button>
                </div>
                <div className="w-4/12 mr-0 ml-auto" >
                    <img className='w-full'
                        alt=''
                        src={hero}
                    />
                </div>
            </div>
        </div>
    )
}

export default Hero;