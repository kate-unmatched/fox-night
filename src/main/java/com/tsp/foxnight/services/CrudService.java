//package com.tsp.foxnight.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Iterables;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.metamodel.Attribute;
//import jakarta.persistence.metamodel.EntityType;
//import jakarta.persistence.metamodel.Type;
//import jakarta.transaction.Transactional;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.SneakyThrows;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.hibernate.validator.internal.util.ReflectionHelper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.util.CastUtils;
//import org.springframework.data.util.Pair;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Service;
//import ru.emias.mcc.domain.entity.Errors;
//import ru.emias.mcc.domain.entity.Utils;
//import ru.emias.mcc.domain.entity.core.DeletableEntity;
//import ru.emias.mcc.domain.entity.core.postgres.User;
//
//import java.time.LocalDateTime;
//import java.time.temporal.Temporal;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static java.util.stream.Collectors.toMap;
//import static org.apache.commons.collections4.IterableUtils.first;
//import static org.apache.commons.lang3.StringUtils.SPACE;
//import static org.apache.commons.lang3.StringUtils.uncapitalize;
//import static ru.emias.mcc.domain.entity.Utils.*;
//
//@Service
//@Transactional
//public class CrudService {
//    private static final char DOT = '.';
//    @Getter
//    private final ObjectMapper objectMapper;
//    @Getter
//    private final EntityManager entityManager;
//    private final Map<Class<?>, Function<String, Object>> converter = Map.of(
//            String.class, Object::toString,
//            Long.class, NumberUtils::createLong,
//            Boolean.class, Boolean::parseBoolean,
//            Integer.class, NumberUtils::createInteger
//    );
//    private final Map<String, Class<?>> className2Class2Identity;
//
//    public CrudService(ObjectMapper objectMapper, EntityManager entityManager) {
//        this.objectMapper = objectMapper;
//        this.entityManager = entityManager;
//        this.className2Class2Identity = Utils.index(entityManager.getMetamodel().getEntities(),
//                t -> uncapitalize(t.getJavaType().getSimpleName()), Type::getJavaType);
//    }
//
//    public <T, ID> T findNullable(Class<T> type, ID id) {
//        return Optional.ofNullable(entityManager.find(type, id)).orElse(null);
//    }
//
//    public <T extends IdentityEntity, ID> T findReached(Class<T> type, ID id) {
//        T t = find(type, id);
//        t.reach(this);
//        return t;
//    }
//
//    public <T, ID> T find(Class<T> type, ID id) {
//        return Optional.ofNullable(entityManager.find(type, id))
//                .orElseThrow(() -> Errors.E103.thr(type.getSimpleName(), id));
//    }
//
//    public <ID, T extends IdentityEntity> List<T> findBy(Class<T> type, Collection<ID> id) {
//        List<T> list = entityManager.createQuery("select e from " + type.getSimpleName() + " e " +
//                " where e.id in ?1 ", type).setParameter(1, id).getResultList();
//        Set<Long> ids = list.stream().map(IdentityEntity::getId).collect(Collectors.toSet());
////        Collection<Object> intersection = CollectionUtils.intersection(ids, id);
//        Collection<Object> disjunction = CollectionUtils.disjunction(id, ids);
//        Errors.E103.thr(disjunction.isEmpty(), type.getSimpleName(), disjunction);
//        return list;
//    }
//
//    public <T> Long count(Class<T> clazz, String field, Object value) {
//        return createQuery(clazz, Collections.singletonMap(field, value), Pageable.unpaged()).getSecond().getSingleResult();
//    }
//
//    public <T> List<T> findAll(Class<T> clazz, String field, Object value) {
//        return createQuery(clazz, Collections.singletonMap(field, value), Pageable.unpaged()).getFirst().getResultList();
//    }
//
//    public <T> Page<T> findAll(Class<T> clazz, Map<String, Object> fields, Pageable page) { // todo
//        Pair<TypedQuery<T>, TypedQuery<Long>> pair = createQuery(clazz, fields, page);
//        List<T> resultList = pair.getFirst().getResultList();
//        Long count = pair.getSecond().getSingleResult();
//        return new PageImpl<>(resultList, page, count);
//    }
//
//    public <T> List<T> findAll(Class<T> clazz, Map<String, Object> fields) {
//        return createQuery(clazz, fields, Pageable.unpaged()).getFirst().getResultList();
//    }
//
//    @NotNull
//    public <T> T find(Class<T> clazz, String field, Object value) {
//        return Iterables.getFirst(findOne(clazz, field, value.getClass(), value.toString()), null);
//    }
//
//    @Nullable
//    public <T> T find(Class<T> clazz, Map<String, Object> fields) {
//        return first(findAll(clazz, fields));
//    }
//
//    public <T> List<T> findOne(Class<T> clazz, String field, Class<?> type, String value) {
//        return entityManager.createQuery("select e from "
//                        + clazz.getSimpleName() + " e where e." + field + " = ?1 ", clazz)
//                .setParameter(1, converter.get(type).apply(value))
//                .getResultList();
//    }
//
//
//    public <T> Pair<TypedQuery<T>, TypedQuery<Long>> createQuery(Class<T> clazz, Map<String, Object> map, Pageable pageable) {
//        return CastUtils.cast(createQuery(clazz, map, pageable, Function.identity()));
//    }
//
//    public <T> Pair<Query, TypedQuery<Long>> createQuery(Class<T> clazz, Map<String, Object> map,
//                                                         Pageable pageable, Function<String, String> q) {
//        map.remove("page");
//        map.remove("size");
//        StringBuilder jpql = new StringBuilder();
//        jpql.append("select e from ").append(clazz.getSimpleName()).append(" e ");
//
//        Map<String, Object> params = new ConcurrentHashMap<>(map);
//        Map<String, Object> paramS = new HashMap<>();
////        Utils.clear(params);
//
//        Set<String> uniqueValues = new HashSet<>();
//        for (String s : params.keySet()) {
//            if (StringUtils.contains(s, DOT)) {
//                String substringBefore = StringUtils.substringBefore(s, DOT);
//                if (Objects.nonNull(getFieldDescriptionMap(clazz, substringBefore))) continue;
//                if (uniqueValues.add(substringBefore)) {
//                    jpql.append(" join ")
//                            .append(StringUtils.capitalize(substringBefore))
//                            .append(SPACE).append(substringBefore).append(SPACE)
//                            .append(" on e.").append(substringBefore).append("Id")
//                            .append(" = ").append(substringBefore).append(".id ");
//                }
//            }
//        }
//        jpql.append(" where e.id is not null ");
//
//        for (String key : params.keySet()) {
//            if (StringUtils.contains(key, DOT)) {
//                classifier(map, jpql, params, paramS, key);
//            } else {
//                classifier(map, jpql, params, paramS, key, key, map.get(key), clazz, "e.");
//            }
//        }
//        paramS.putAll(params);
//
//        jpql.append(" order by ");
//        if (pageable.getSort().isSorted()) {
//            jpql.append(pageable.getSort().get().map(o ->
//                    StringUtils.join("e.", o.getProperty(),
//                            StringUtils.SPACE, o.getDirection())
//            ).collect(Collectors.joining(", ")));
//        } else {
//            jpql.append(" e.id desc ");
//        }
//
//        Query query;
//        if (pageable.isPaged()) {
//            jpql.append(" limit ").append(pageable.getPageSize())
//                    .append(" offset ").append(pageable.getOffset());
//        }
//        String pql = q.apply(jpql.toString());
//
//        if (!StringUtils.contains(pql, " group by")) {//fixme costyl
//            query = entityManager.createQuery(pql, clazz);
//        } else {
//            query = entityManager.createQuery(pql);
//        }
//        TypedQuery<Long> count = entityManager.createQuery(StringUtils.substringBefore(StringUtils.replace(
//                jpql.toString(), "select e", "select count(e)"), "order by"), Long.class);
//        for (Map.Entry<String, Object> entry : paramS.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//            count.setParameter(entry.getKey(), entry.getValue());
//        }
////        if (pageable.isPaged()) {
////            query = query.setFirstResult(Math.toIntExact(pageable.getOffset()))
////                    .setMaxResults(pageable.getPageSize());
////        }//        PageableExecutionUtils.getPage()
//        return Pair.of(query, count);
//    }
//
//    @SneakyThrows
//    private void classifier(Map<String, Object> map, StringBuilder jpql,
//                            Map<String, Object> params,
//                            Map<String, Object> paramS,
//                            String key) {
//        String[] split = StringUtils.split(key, DOT);
//        Class<?> className = Utils.findNearest(className2Class2Identity, split[0]);
//        classifier(map, jpql, params, paramS, key, split[1], params.get(key), className, StringUtils.EMPTY);
//    }
//
//    private void classifier(Map<String, Object> map, StringBuilder jpql,
//                            Map<String, Object> params,
//                            Map<String, Object> paramS,
//                            String key, String newKey,
//                            Object value,
//                            Class<?> clazz,
//                            String prefix) {
//        classifier(map, jpql, params, paramS, key, newKey, value, clazz, prefix, " and ");
//    }
//
//    private void classifier(Map<String, Object> map, StringBuilder jpql,
//                            Map<String, Object> params,
//                            Map<String, Object> paramS,
//                            String key, String newKey,
//                            Object value,
//                            Class<?> clazz,
//                            String prefix,
//                            String operator) {
//        if (WHERE_OR.equals(newKey)) {
//            Map<String, Object> or = CastUtils.cast(params.remove(WHERE_OR));
//            jpql.append(operator).append(" ( false");
//            params.putAll(or);
//            for (String k : or.keySet()) {
//                classifier(or, jpql, params, paramS, k, k, or.get(k),
//                        clazz, prefix, StringUtils.wrap(WHERE_OR, SPACE));
//            }
//            jpql.append(" ) ");
//            return;
//        }
//        if (WHERE_ECQL.equals(newKey)) { // todo db inject
//            jpql.append(operator).append(params.remove(WHERE_ECQL));
//            return;
//        }
//
//        Class<?> aClass = getFieldDescriptionMap(clazz, newKey);
//
//        String v = value.toString();
//        if (StringUtils.endsWith(v, "null")) {
//            jpql.append(operator).append(prefix).append(key).append(" is ");
//            if (StringUtils.startsWith(v, "!")) {
//                jpql.append(" not null ");
//            } else {
//                jpql.append(" null ");
//            }
//            params.remove(key);
//            return;
//        }
//
//        if (String.class.equals(aClass)) {
//            jpql.append(operator).append("lower(").append(prefix).append(key).append(")");
//            if (ReflectionHelper.isIterable(value.getClass())) {
//                jpql.append(" in :").append(newKey);
//            } else {
//                jpql.append(" like :").append(newKey);
//                params.replace(key, lowerLike(value));
//            }
//        }
//        if (AbstractEntity.class.isAssignableFrom(aClass)) {
//            if (value instanceof Number) {
//                jpql.append(operator).append(prefix).append(key).append(" in (:").append(newKey).append(")");
//            } else {
//                jpql.append(operator).append(" lower(").append(prefix).append(key).append(") like :").append(newKey);
//                params.replace(key, lowerLike(map.get(key)));
//            }
//        }
//        if (Temporal.class.isAssignableFrom(aClass)) {
//            String field = between(key);
//            String f = field + BETWEEN[0];
//            String t = field + BETWEEN[1];
//
//            if (paramS.containsKey(f) || paramS.containsKey(t)) return;
//            boolean isLdt = aClass.equals(LocalDateTime.class);
//            Temporal from = date(params.get(f), true, isLdt);
//            Temporal to = date(params.get(t), false, isLdt);
//
//            jpql.append(operator).append(prefix).append(field).append(" between :")
//                    .append(f).append(" and :").append(t);
//
//            params.put(f, from);
//            params.put(t, to);
//            paramS.put(f, from);
//            paramS.put(t, to);
//        }
//        if (Number.class.isAssignableFrom(aClass)) {
//            jpql.append(operator).append(prefix).append(key).append(" in :").append(newKey);
//            if (!ReflectionHelper.isIterable(value.getClass())) {
//                params.replace(key, converter.get(aClass).apply(map.get(key).toString()));
//            }
//        }
//        if (Enum.class.isAssignableFrom(aClass)) { // fixme mb
//            jpql.append(operator).append(prefix).append(key).append(" = :").append(newKey);
//            // params.replace(key, converter.get(Enum.class).apply(map.get(key).toString()));
//        }
//        if (Boolean.class.isAssignableFrom(aClass)) {
//            jpql.append(operator).append(prefix).append(key).append(" = :").append(newKey);
//            params.replace(key, converter.get(Boolean.class).apply(map.get(key).toString()));
//        }
//        paramS.put(newKey, params.remove(key));
//    }
//
//    private Class<?> getFieldDescriptionMap(Class<?> clazz, String field) {
//        if (IdentityEntity.Fields.id.equals(field)) return Long.class;
//        if (StringUtils.containsAny(field, BETWEEN)) {
//            field = between(field);
//        }
//
//        Map<String, Map<String, ? extends Class<?>>> clazz2attr2type = getMetaMap();
//
//        return clazz2attr2type.get(clazz.getSimpleName()).get(field);
//    }
//
//    public Map<String, Map<String, ? extends Class<?>>> getMetaMap() {
//        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
//        return entities.stream().collect(toMap(EntityType::getName,
//                entityType -> entityType.getAttributes().stream()
//                        .collect(toMap(Attribute::getName, Attribute::getJavaType))));
//    }
//
//    public <E extends IdentityEntity> E update(Long id, E newEntity, User user) {
//        IdentityEntity old = find(newEntity.getClass(), id);
//        newEntity.setId(id);
//        Utils.copyNn(newEntity, old);
//        old.onUpdate(this, user);
//        entityManager.merge(old);
//        return newEntity;
//    }
//
//    public <E extends IdentityEntity> E save(E entity, User user) {
//        Consumer<IdentityEntity> consumer = entity.onSelfCreate(this, user);
//        E saved = entityManager.merge(entity);
//        consumer.accept(saved);
//        entity.setId(saved.getId());
//        for (Object o : entity.getMap().values()) {
//            if (o instanceof Collection<?> collection) {
//                collection.forEach(entityManager::merge);
//            } else {
//                entityManager.merge(o);
//            }
//        }
//        return saved;
//    }
//
//    public <E extends AbstractEntity<Long>> Long delete(Long id, Class<E> clazz) {
//        return delete(find(clazz, id));
//    }
//
//    public <ID, E extends AbstractEntity<ID>> void delete(Collection<E> entity) {
//        entity.forEach(this::delete);
//    }
//
//    public <ID, E extends AbstractEntity<ID>> ID delete(E entity) {
//        entity.onPreDelete(this, null); // todo check
//        if (entity instanceof DeletableEntity deletable) {
//            Errors.E105.thr(Objects.isNull(deletable.getDeletedDate()), entity.getId());
//            deletable.setDeletedDate(LocalDateTime.now());
//            entityManager.merge(entity);
//        } else {
//            entityManager.remove(entity);
//        }
//        entity.onDelete(this, null); // todo check
//        return entity.getId();
//    }
//
//    public <E extends AbstractEntity<Long>> void merge(Collection<E> entity) {
//        entity.forEach(this::merge);
//    }
//
//    public <E extends AbstractEntity<Long>> E merge(E entity) {
//        return entityManager.merge(entity);
//    }
//
///*    public void delete(Class<?> clazz, Collection<Long> entities) {
//        List<?> objects = findBy(clazz, entities);
//        objects.forEach(entityManager::remove);
//    }*/
//
//    public User getUserByLogin(String login) {//fixme mb not like
//        return find(User.class, User.Fields.login, login);
//    }
//}
