public class CuncurrentReader{
	void readerPage(char Sequence source) {
		final List<ImageInfo> imageInfos = scanForImageInfo(source);
		final List<ImageData> imageData = new ArrayList<ImageData>();
		Runnable downImg = new Runnable() {
			public void run() {
				for(ImageInfo:ImageInfo.imageInfos)
					imageData.add(ImageInfo.downloadImage());
			}
		};
		Thread t = new Thread(downImg);
		t.start();
		renderText(source);
		try{
			t.join();
		}catch(InterruptedException e) {
			t.Interrupt();
		}
		for(ImageData data:imageData) renderImage(data);
	}
}