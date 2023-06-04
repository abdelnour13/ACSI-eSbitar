function ContactUs() {
    return (
      <div className="mb-2" >
        <h1 className="text-4xl font-bold text-c-blue-950 m-2">Contact us</h1>
        <div className="w-5/12 r-sm:w-full m-auto" >
            <form className="bg-c-blue-100 border border-c-blue-950 flex flex-col gap-3 p-4" >
                <label>
                    Subject
                </label>
                <input 
                    className="outline-0 p-2 border-c-blue-950 border rounded"
                    placeholder="Subject"
                />
                <label>
                    Message
                </label>
                <textarea 
                    className="h-28 p-2 border-c-blue-950 border rounded outline-0"
                    placeholder="your opinions ..."
                />
                <button className="p-2 border-2 border-c-blue-950 rounded
                    bg-white hover:bg-c-blue-950 transition-colors
                    text-xl hover:text-white text-c-blue-950
                " >
                    send
                </button>
            </form>
        </div>
      </div>
    )
}

export default ContactUs;