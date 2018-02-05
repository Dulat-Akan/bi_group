package bi.bigroup.life.utils.stetho;

import android.app.Application;
import android.os.Build;

import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.elements.Document;
import com.facebook.stetho.inspector.elements.DocumentProviderFactory;
import com.facebook.stetho.inspector.elements.android.AndroidDocumentConstants;
import com.facebook.stetho.inspector.elements.android.AndroidDocumentProviderFactory;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.module.CSS;
import com.facebook.stetho.inspector.protocol.module.Console;
import com.facebook.stetho.inspector.protocol.module.DOM;
import com.facebook.stetho.inspector.protocol.module.Debugger;
import com.facebook.stetho.inspector.protocol.module.HeapProfiler;
import com.facebook.stetho.inspector.protocol.module.Inspector;
import com.facebook.stetho.inspector.protocol.module.Network;
import com.facebook.stetho.inspector.protocol.module.Page;
import com.facebook.stetho.inspector.protocol.module.Profiler;

import java.util.ArrayList;
import java.util.List;

public class StethoCustomConfigBuilder {
    private final Application context;
    private boolean viewHierarchyInspectorEnabled = true;

    public StethoCustomConfigBuilder(Application context) {
        this.context = context;
    }

    /**
     * A convenience method to be able to configure Stetho without View Hierarchy Inspector, because VHI significantly
     * slows down UI.
     */
    public StethoCustomConfigBuilder viewHierarchyInspectorEnabled(boolean enabled) {
        viewHierarchyInspectorEnabled = enabled;
        return this;
    }

    public Stetho.Initializer build() {
        return Stetho.newInitializerBuilder(context).enableWebKitInspector(new InspectorModulesProvider() {
            @Override
            public Iterable<ChromeDevtoolsDomain> get() {
                List<ChromeDevtoolsDomain> modules = new ArrayList<>();
                modules.add(new Console());
                modules.add(new Debugger());
                modules.add(new HeapProfiler());
                modules.add(new Inspector());
                modules.add(new Network(context));
                modules.add(new Page(context));
                modules.add(new Profiler());
                if (viewHierarchyInspectorEnabled) {
                    DocumentProviderFactory documentModel = resolveDocumentProvider(context);
                    if (documentModel != null) {
                        Document document = new Document(documentModel);
                        modules.add(new DOM(document));
                        modules.add(new CSS(document));
                    }
                }
                return modules;
            }
        }).build();
    }

    private static DocumentProviderFactory resolveDocumentProvider(Application context) {
        if (Build.VERSION.SDK_INT >= AndroidDocumentConstants.MIN_API_LEVEL) {
            return new AndroidDocumentProviderFactory(context);
        }
        return null;
    }
}
