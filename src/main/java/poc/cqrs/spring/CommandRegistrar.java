package poc.cqrs.spring;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import poc.cqrs.command.CommandRegistry;
import poc.cqrs.command.impl.DefaultCommandRegistry;

/**
 * Permet de créer le bean {@link CommandRegistry} en cherchant dynamiquement les {@link Command}. 
 */
/* package */ class CommandRegistrar implements ImportBeanDefinitionRegistrar  {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Class<?>> commands = findCommands();
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DefaultCommandRegistry.class);
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(commands);
		beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		beanDefinition.setSynthetic(true);
		registry.registerBeanDefinition(DefaultCommandRegistry.class.getName(), beanDefinition);
	}

	private Map<String, Class<?>> findCommands() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Command.class));
		// TODO scan package
		Set<BeanDefinition> defs = scanner.findCandidateComponents("poc");
		return defs.stream()
				.map(def -> def.getBeanClassName())
				.map(className -> toClass(className))
				.collect(Collectors.toMap(clazz -> extractCommandName(clazz), clazz -> clazz));
	}
	
	private String extractCommandName(Class<?> commandClass) {
		Command command = commandClass.getAnnotation(Command.class);
		if (command == null) {
			throw new IllegalStateException(String.format("L'annotation %s est absente de la classe %s", Command.class.getSimpleName(), commandClass.getSimpleName()));
		}
		return command.name();
	}
	
	private Class<?> toClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
