package org.abratuhi.acmtimus.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.abratuhi.acmtimus.model.Problem;
import org.abratuhi.acmtimus.model.ProblemRef;
import org.abratuhi.acmtimus.parse.ParseException;
import org.abratuhi.acmtimus.parse.ProblemParser;
import org.abratuhi.acmtimus.parse.ProblemSetParser;
import org.abratuhi.acmtimus.preferencies.Prefs;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;

public class ProblemSetView extends ViewPart {
	public ProblemSetView() {
	}

	private TableViewer viewer;
	private Browser browser;
	private Button export;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ProblemSetContentProvider());
		viewer.setInput(new String[] { "one", "two" });

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event
							.getSelection();
					ProblemRef pf = (ProblemRef) selection.getFirstElement();
					ProblemParser pp = new ProblemParser();
					try {
						Problem p = pp.parse(pf.getId());
						browser.setText(p.getRaw());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		Composite comp1 = new Composite(parent, SWT.NONE);
		comp1.setLayout(new RowLayout(SWT.VERTICAL));

		browser = new Browser(comp1, SWT.ALL);
		browser.setLayoutData(new RowData(294, 426));

		export = new Button(comp1, SWT.CENTER);
		export.setText("Export");
		export.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					String projectDirectoryName = Prefs.get(Prefs.PREF_PROJECT);
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					File workspaceDirectory = workspace.getRoot().getLocation()
							.toFile();
					File projectDirectory = new File(workspaceDirectory,
							projectDirectoryName);

					if (!projectDirectory.exists()) {
						// create eclipse project
						IProgressMonitor progressMonitor = new NullProgressMonitor();
						IWorkspaceRoot root = workspace.getRoot();
						IProject project = root
								.getProject(projectDirectoryName);
						project.create(progressMonitor);
						project.open(progressMonitor);

						// set the java project nature
						IProjectDescription description = project
								.getDescription();
						description
								.setNatureIds(new String[] { JavaCore.NATURE_ID });
						project.setDescription(description, null);

						// create java project
						IJavaProject javaProject = JavaCore.create(project);

						// add bin/ouput folder
						IFolder binFolder = project.getFolder("bin");
						binFolder.create(false, true, null);
						javaProject.setOutputLocation(binFolder.getFullPath(),
								null);

						List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
						IVMInstall vmInstall = JavaRuntime
								.getDefaultVMInstall();
						LibraryLocation[] locations = JavaRuntime
								.getLibraryLocations(vmInstall);
						for (LibraryLocation element : locations) {
							entries.add(JavaCore.newLibraryEntry(
									element.getSystemLibraryPath(), null, null));
						}
						// add libs to project class path
						javaProject.setRawClasspath(entries
								.toArray(new IClasspathEntry[entries.size()]),
								null);

						// create source folder
						IFolder sourceFolder = project.getFolder("src");
						sourceFolder.create(false, true, null);

						IPackageFragmentRoot froot = javaProject
								.getPackageFragmentRoot(sourceFolder);
						IClasspathEntry[] oldEntries = javaProject
								.getRawClasspath();
						IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
						System.arraycopy(oldEntries, 0, newEntries, 0,
								oldEntries.length);
						newEntries[oldEntries.length] = JavaCore
								.newSourceEntry(froot.getPath());
						javaProject.setRawClasspath(newEntries, null);
					}
					
					IWorkspaceRoot root = workspace.getRoot();
					IProject project = root.getProject(projectDirectoryName);
					IJavaProject javaProject = JavaCore.create(project);
					
					IFolder sourceFolder = project.getFolder("src");
					IPackageFragmentRoot froot = javaProject
							.getPackageFragmentRoot(sourceFolder);
					String packageName = Prefs.get(Prefs.PREF_PACKAGE);
					IPackageFragment pack = froot.createPackageFragment(packageName, false, null);
					
					IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					ProblemRef pf = (ProblemRef) selection.getFirstElement();
					ICompilationUnit cu = pack.createCompilationUnit(pf2classfilename(pf), pf2classsource(pf), false, null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private String pf2classname(ProblemRef pf) {
		return new String("Problem" + pf.getId());
	}
	
	private String pf2classfilename(ProblemRef pf) {
		return new String("Problem" + pf.getId() + ".java");
	}
	
	private String pf2classsource(ProblemRef pf){
		StringBuffer sb = new StringBuffer();
		String classname = pf2classname(pf);
		sb.append("public class " + classname + " { \n");
		sb.append("} \n");
		return sb.toString();
	}

}

class ProblemSetContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}

	@Override
	public Object[] getElements(Object parent) {
		try {
			ProblemSetParser psp = new ProblemSetParser();
			List<ProblemRef> pfs = psp.parse(ProblemSetParser.PROBLEM_SET_URL);
			return pfs.toArray();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}