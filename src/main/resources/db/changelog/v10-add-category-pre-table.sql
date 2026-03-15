INSERT INTO categories 
(created_at, created_by, is_active, is_deleted, updated_at, updated_by, description, image, name)
VALUES

(NOW(), 1, true, false, NOW(), 1, 'Daily personal tasks and routines', 'personal.png', 'Personal'),

(NOW(), 1, true, false, NOW(), 1, 'Office or professional work tasks', 'work.png', 'Work'),

(NOW(), 1, true, false, NOW(), 1, 'Shopping lists and purchase reminders', 'shopping.png', 'Shopping'),

(NOW(), 1, true, false, NOW(), 1, 'Health, fitness and workout schedules', 'fitness.png', 'Fitness'),

(NOW(), 1, true, false, NOW(), 1, 'Study plans and learning goals', 'study.png', 'Study'),

(NOW(), 1, true, false, NOW(), 1, 'Home related chores and maintenance', 'home.png', 'Home'),

(NOW(), 1, true, false, NOW(), 1, 'Travel planning and trip tasks', 'travel.png', 'Travel'),

(NOW(), 1, true, false, NOW(), 1, 'Financial tasks like bill payments', 'finance.png', 'Finance'),

(NOW(), 1, true, false, NOW(), 1, 'Important events and special days', 'events.png', 'Events'),

(NOW(), 1, true, false, NOW(), 1, 'Project related planning and execution', 'project.png', 'Projects'),

(NOW(), 1, true, false, NOW(), 1, 'Hobbies and personal interests', 'hobby.png', 'Hobby'),

(NOW(), 1, true, false, NOW(), 1, 'Miscellaneous uncategorized tasks', 'misc.png', 'Others');