package net.yoojia.dataprovider.sample;

import net.yoojia.dataprovider.ProviderLauncher;
import net.yoojia.dataprovider.ProviderManager;
import net.yoojia.dataprovider.logic.CategoryProvider;
import android.app.Application;

public class TheApp extends Application {

	static{
		// Privider框架通过系统调用Minifest.XML配置的Provider而完成启动过程，
		// 所以需要在App 静态块中将其注册到管理器中。
		ProviderLauncher.setDebugMode(true);

		ProviderManager.register(CategoryProvider.class);
		ProviderLauncher.authority("xxoo.com.com.dn");
	}
}
