package org.abratuhi.acmtimus.actions;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class SubmitAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Shell shell = HandlerUtil.getActiveShell(event);
			ISelection sel = HandlerUtil.getActiveMenuSelection(event);
			IStructuredSelection selection = (IStructuredSelection) sel;
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof ICompilationUnit) {
				submitSolution(shell, firstElement);

			} else {
				MessageDialog.openInformation(shell, "Info",
						"Please select a Java source file");
			}
		} catch (Exception e) {
			throw new ExecutionException("", e);
		}
		return null;
	}

	private void submitSolution(Shell shell, Object firstElement)
			throws CoreException {
		ICompilationUnit cu = (ICompilationUnit) firstElement;
		IResource res = cu.getResource();
		File file = res.getLocation().toFile();
		System.out.println("directory = " + file.getAbsolutePath());
	}

}
